package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.common.ActivityConstants;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.ActivityInfoMapper;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.service.ActivityGoodsGroupService;
import com.sida.dcloud.activity.service.ActivityGoodsService;
import com.sida.dcloud.activity.service.ActivityInfoService;
import com.sida.dcloud.activity.vo.ActivityGoodsGroupVo;
import com.sida.dcloud.activity.vo.ActivityInfoAndGoodsVo;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.po.common.IdNamePair;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.DistributedLock;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActivityInfoServiceImpl extends BaseServiceImpl<ActivityInfo> implements ActivityInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityInfoServiceImpl.class);

    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + ActivityInfoServiceImpl.class.getName();
    private static final String LOCK_KEY_CHECK_ACTIVITY_STATUS = "LOCK_KEY_CHECK_ACTIVITY_STATUS_" + ActivityInfoServiceImpl.class.getName();

    @Autowired
    private ActivityInfoMapper activityInfoMapper;
    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private ActivityGoodsService activityGoodsService;
    @Autowired
    private ActivityGoodsGroupService activityGoodsGroupService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IMybatisDao<ActivityInfo> getBaseDao() {
        return activityInfoMapper;
    }

    @Override
    public Page<ActivityInfoVo> findPageList(ActivityInfo po) {
//        LOG.info("每页 {} 条", po.getS());
        PageHelper.startPage(po.getP(),po.getS());
        Map<String, Object> map = (Map<String, Object>)redisUtil.getRegionDatasByKey(RedisKey.SYS_REGION_CACHE_WITH_ALL_BY_FLAT);
        List<ActivityInfoVo> voList = activityInfoMapper.findVoList(po).stream().map(vo -> {
            vo.setRegionName(((IdNamePair)map.get(vo.getRegionId())).getName());
            return vo;
        }).collect(Collectors.toList());
        return (Page) voList;
    }

    @Override
    public void increaseCommentCount(String activityId, int count) {
        activityInfoMapper.increaseCommentCount(activityId, count);
    }

    @Override
    public void increaseSignCount(String activityId, int count) {
        activityInfoMapper.increaseSignCount(activityId, count);
    }

    @Override
    public void increaseFavoriteCount(String activityId, int count) {
        activityInfoMapper.increaseFavoriteCount(activityId, count);
    }

    @Override
    public ActivityInfoAndGoodsVo findOneWithGoods(String activityId) {
        ActivityInfoAndGoodsVo activityInfoAndGoodsVo = new ActivityInfoAndGoodsVo();
        ActivityInfo one = activityInfoMapper.selectByPrimaryKey(activityId);
        BeanUtils.copyProperties(one, activityInfoAndGoodsVo);
        StringBuilder builder = new StringBuilder(activityId);
        one.getChildren().stream().map(child -> child.getId()).forEach(id -> builder.append(",").append(id));
        String activityIds = builder.toString();//Xiruo.insertSingleQuoteToString(builder.toString());
        List<ActivityGoodsGroupVo> gList = activityGoodsGroupService.findGroupListByActivityIds(activityIds);
        gList.forEach(group -> {
            long[] arr = group.getActivityGoodsVoList().stream()
                    .map(goods -> new long[] {goods.getStartTime().getTime(), goods.getEndTime().getTime()})
                    .reduce((sarr, earr) -> {
                        if(sarr[0] > earr[0]) {
                            sarr[0] = earr[0];
                        }
                        if(sarr[1] < earr[1]) {
                            sarr[1] = earr[1];
                        }
                        return sarr;
                    }).get();
            group.setStartTime(new Date(arr[0]));
            group.setEndTime(new Date(arr[1]));
        });
        activityInfoAndGoodsVo.setActivityGoodsGroupVoList(gList);
        activityInfoAndGoodsVo.setActivityGoodsVoList(activityGoodsService.findGoodsListByActivityIds(activityIds));
        return activityInfoAndGoodsVo;
    }

    /**
     * 新增和更新操作都需要进行重复检验，因此要进行锁互斥
     * @param po
     * @return
     */
    @Override
    public int insert(ActivityInfo po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (activityInfoMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("活动名称已经存在，不能重复");
                }
                result = super.insert(po);
            } catch(Exception e) {
                LOG.error("Insert method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    /**
     * 新增和更新操作都需要进行重复检验，因此要进行锁互斥
     * @param po
     * @return
     */
    @Override
    public int updateByPrimaryKey(ActivityInfo po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (activityInfoMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("活动名称已经存在，不能重复");
                }
                result = super.updateByPrimaryKey(po);
            } catch(Exception e) {
                LOG.error("UpdateByPrimaryKey method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    /**
     * 活动状态更新需加锁
     * @param activityId
     * @param activityStatus
     * @return
     */
    @Override
    public int updateActivityStatus(String activityId, String activityStatus) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_ACTIVITY_STATUS, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_ACTIVITY_STATUS);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_ACTIVITY_STATUS);
            try {
                result = activityInfoMapper.updateActivityStatus(activityId, activityStatus);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".updateActivityStatus method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_ACTIVITY_STATUS);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_ACTIVITY_STATUS + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_ACTIVITY_STATUS, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_ACTIVITY_STATUS);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_ACTIVITY_STATUS);
            try {
                if (activityInfoMapper.checkCountForActivityStatus(ids, ActivityConstants.REMOVABLE_ACTIVITY_STATUS_FLAG) > 0) {
                    throw new ActivityException("已发布的活动不允许删除");
                }
                result = activityInfoMapper.deleteByPrimaryKeys(ids);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".deleteByPrimaryKeys method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_ACTIVITY_STATUS);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_ACTIVITY_STATUS + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }
}