package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.common.ActivityConstants;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.ActivityInfoMapper;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.service.ActivityInfoService;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.DistributedLock;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityInfoServiceImpl extends BaseServiceImpl<ActivityInfo> implements ActivityInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityInfoServiceImpl.class);

    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + ActivityInfoServiceImpl.class.getName();
    private static final String LOCK_KEY_CHECK_ACTIVITY_STATUS = "LOCK_KEY_CHECK_ACTIVITY_STATUS_" + ActivityInfoServiceImpl.class.getName();

    @Autowired
    private ActivityInfoMapper activityInfoMapper;
    @Autowired
    private DistributedLock distributedLock;

    @Override
    public IMybatisDao<ActivityInfo> getBaseDao() {
        return activityInfoMapper;
    }

    @Override
    public Page<ActivityInfoVo> findPageList(ActivityInfo po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<ActivityInfoVo> voList = activityInfoMapper.findVoList(po);
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
     *
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