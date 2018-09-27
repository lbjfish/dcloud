package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.ActivityGoodsRelGroupMapper;
import com.sida.dcloud.activity.po.ActivityGoodsRelGroup;
import com.sida.dcloud.activity.po.ActivityRelHonored;
import com.sida.dcloud.activity.service.ActivityGoodsRelGroupService;
import com.sida.dcloud.activity.service.ActivityRelHonoredService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.DistributedLock;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityGoodsRelGroupServiceImpl extends BaseServiceImpl<ActivityGoodsRelGroup> implements ActivityGoodsRelGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityGoodsRelGroupServiceImpl.class);

    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private ActivityGoodsRelGroupMapper activityGoodsRelGroupMapper;

    @Override
    public IMybatisDao<ActivityGoodsRelGroup> getBaseDao() {
        return activityGoodsRelGroupMapper;
    }

    @Override
    public int deleteByGroupId(String groupId) {
        return activityGoodsRelGroupMapper.deleteByGroupId(groupId);
    }

    /**
     * 多对多关联时，关联两端的删除操作和建立关联操作需要互斥，因此进行分布式锁控制，锁名称共享
     * @param list
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int saveOrUpdate(List<ActivityGoodsRelGroup> list) {
        int result = -1;
        if(!list.isEmpty()) {
            boolean lock = distributedLock.lock(LOCK_KEY_CHECK_REMOVABLE, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);

            if(!lock) {
                LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_REMOVABLE);
                throw new ActivityException("获取锁失败，请稍后重试。。。");
            } else {
                LOG.debug("Get lock success : " + LOCK_KEY_CHECK_REMOVABLE);
                try {
                    activityGoodsRelGroupMapper.deleteByGroupId(list.get(0).getGroupId());
                    result = activityGoodsRelGroupMapper.batchInsert(list);
                } catch(Exception e) {
                    LOG.error(getClass().getName() + ".saveOrUpdate method occured exception", e);
                } finally {
                    boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_REMOVABLE);
                    LOG.debug("Release lock : " + LOCK_KEY_CHECK_REMOVABLE + (releaseResult ? " success" : " failed"));
                }
            }
        }
        return result;
    }
}