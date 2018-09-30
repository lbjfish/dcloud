package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.ActivityGoodsGroupMapper;
import com.sida.dcloud.activity.po.ActivityGoodsGroup;
import com.sida.dcloud.activity.service.ActivityGoodsGroupService;
import com.sida.dcloud.activity.service.ActivityGoodsRelGroupService;
import com.sida.dcloud.activity.vo.ActivityGoodsGroupVo;
import com.sida.xiruo.common.util.Xiruo;
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
public class ActivityGoodsGroupServiceImpl extends BaseServiceImpl<ActivityGoodsGroup> implements ActivityGoodsGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityGoodsGroupServiceImpl.class);
    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + ActivityGoodsServiceImpl.class.getName();

    @Autowired
    private DistributedLock distributedLock;

    @Autowired
    private ActivityGoodsGroupMapper activityGoodsGroupMapper;

    @Override
    public IMybatisDao<ActivityGoodsGroup> getBaseDao() {
        return activityGoodsGroupMapper;
    }

    /**
     * 新增和更新操作都需要进行重复检验，因此要进行锁互斥
     * @param po
     * @return
     */
    @Override
    public int insert(ActivityGoodsGroup po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);

        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (activityGoodsGroupMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("同一活动下组名称已经存在，不能重复");
                }
                result = super.insert(po);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".insert method occured exception", e);
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
    public int updateByPrimaryKey(ActivityGoodsGroup po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (activityGoodsGroupMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("同一活动下组名称已经存在，不能重复");
                }
                result = super.updateByPrimaryKey(po);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".UpdateByPrimaryKey method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    /**
     * 多对多关联时，关联两端的删除操作和建立关联操作需要互斥，因此进行分布式锁控制，锁名称共享
     * @param ids
     * @return
     */
    @Override
    public int deleteByPrimaryKeys(String ids) {
        boolean lock = distributedLock.lock(ActivityGoodsRelGroupService.LOCK_KEY_CHECK_REMOVABLE, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + ActivityGoodsRelGroupService.LOCK_KEY_CHECK_REMOVABLE);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + ActivityGoodsRelGroupService.LOCK_KEY_CHECK_REMOVABLE);
            try {
                if (activityGoodsGroupMapper.checkRemovableByRel(Xiruo.insertSingleQuoteToString(ids)) > 0) {
                    throw new ActivityException("商品组已经在其他地方（活动商品）被引用，不能删除");
                }
                result = super.deleteByPrimaryKeys(ids);
            } catch(Exception e) {
                LOG.error("deleteByPrimaryKeys method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(ActivityGoodsRelGroupService.LOCK_KEY_CHECK_REMOVABLE);
                LOG.debug("Release lock : " + ActivityGoodsRelGroupService.LOCK_KEY_CHECK_REMOVABLE + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    @Override
    public Page<ActivityGoodsGroupVo> findPageList(ActivityGoodsGroup po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<ActivityGoodsGroupVo> voList = activityGoodsGroupMapper.findVoList(po);
        return (Page) voList;
    }

    @Override
    public List<ActivityGoodsGroupVo> findGroupListByActivityId(String activityId) {
        return activityGoodsGroupMapper.findGroupListByActivityId(activityId);
    }

    @Override
    public List<ActivityGoodsGroupVo> findGroupListByGoodsId(String goodsId) {
        return activityGoodsGroupMapper.findGroupListByGoodsId(goodsId);
    }

    @Override
    public List<ActivityGoodsGroup> findListByIds(String ids) {
        return activityGoodsGroupMapper.findListByIds(ids);
    }

    @Override
    public int updateGroupPayPrice(String groupId, Double payPrice) {
        return activityGoodsGroupMapper.updateGroupPayPrice(groupId, payPrice);
    }
}