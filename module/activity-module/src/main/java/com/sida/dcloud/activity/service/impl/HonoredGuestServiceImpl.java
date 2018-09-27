package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.HonoredGuestMapper;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.service.ActivityRelHonoredService;
import com.sida.dcloud.activity.service.HonoredGuestService;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
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
public class HonoredGuestServiceImpl extends BaseServiceImpl<HonoredGuest> implements HonoredGuestService {
    private static final Logger LOG = LoggerFactory.getLogger(HonoredGuestServiceImpl.class);

    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + HonoredGuestServiceImpl.class.getName();

    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private HonoredGuestMapper honoredGuestMapper;


    @Override
    public IMybatisDao<HonoredGuest> getBaseDao() {
        return honoredGuestMapper;
    }

    /**
     * 新增和更新操作都需要进行重复检验，因此要进行锁互斥
     * @param po
     * @return
     */
    @Override
    public int insert(HonoredGuest po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (honoredGuestMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("身份证号码和移动电话号码已经存在，不能重复");
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
    public int updateByPrimaryKey(HonoredGuest po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (honoredGuestMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("身份证号码和移动电话号码已经存在，不能重复");
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

    @Override
    public Page<HonoredGuestVo> findPageList(HonoredGuest po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<HonoredGuestVo> voList = honoredGuestMapper.findVoList(po);
        return (Page) voList;
    }

    @Override
    public List<HonoredGuestVo> findVoListByScheduleId(String scheduleId) {
        return honoredGuestMapper.findVoListByScheduleId(scheduleId);
    }

    /**
     * 多对多关联时，关联两端的删除操作和建立关联操作需要互斥，因此进行分布式锁控制，锁名称共享
     * @param ids
     * @return
     */
    @Override
    public int deleteByPrimaryKeys(String ids) {
        boolean lock = distributedLock.lock(ActivityRelHonoredService.LOCK_KEY_CHECK_REMOVABLE, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + ActivityRelHonoredService.LOCK_KEY_CHECK_REMOVABLE);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + ActivityRelHonoredService.LOCK_KEY_CHECK_REMOVABLE);
            try {
                if (honoredGuestMapper.checkRemovableByRel(Xiruo.insertSingleQuoteToString(ids)) > 0) {
                    throw new ActivityException("嘉宾已经在其他地方（活动安排）被引用，不能删除");
                }
                result = super.deleteByPrimaryKeys(ids);
            } catch(Exception e) {
                LOG.error("deleteByPrimaryKeys method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(ActivityRelHonoredService.LOCK_KEY_CHECK_REMOVABLE);
                LOG.debug("Release lock : " + ActivityRelHonoredService.LOCK_KEY_CHECK_REMOVABLE + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }
}