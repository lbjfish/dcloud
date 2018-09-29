package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.common.ActivityConstants;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.ActivityOrderMapper;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.service.ActivityOrderService;
import com.sida.dcloud.activity.vo.ActivityOrderVo;
import com.sida.dcloud.activity.vo.ActivityOrderVo;
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
public class ActivityOrderServiceImpl extends BaseServiceImpl<ActivityOrder> implements ActivityOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityOrderServiceImpl.class);
    
    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + ActivityOrderServiceImpl.class.getName();
    private static final String LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS = "LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS_" + ActivityOrderServiceImpl.class.getName();

    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private ActivityOrderMapper activityOrderMapper;

    @Override
    public IMybatisDao<ActivityOrder> getBaseDao() {
        return activityOrderMapper;
    }

    @Override
    public Page<ActivityOrderVo> findPageList(ActivityOrder po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<ActivityOrderVo> voList = activityOrderMapper.findVoList(po);
        return (Page) voList;
    }

    /**
     * 活动状态更新需加锁
     * @param activityId
     * @param activityOrderStatus
     * @return
     */
    @Override
    public int updateActivityOrderStatus(String activityId, String activityOrderStatus) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
            try {
                result = activityOrderMapper.updateActivityOrderStatus(activityId, activityOrderStatus);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".updateActivityOrderStatus method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
            try {
                if (activityOrderMapper.checkCountForActivityOrderStatus(ids, ActivityConstants.REMOVABLE_ACTIVITY_ORDER_STATUS_FLAGS) > 0) {
                    throw new ActivityException("次状态订单不允许删除");
                }
                result = activityOrderMapper.deleteByPrimaryKeys(ids);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".deleteByPrimaryKeys method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_ACTIVITY_ORDER_STATUS + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }
}