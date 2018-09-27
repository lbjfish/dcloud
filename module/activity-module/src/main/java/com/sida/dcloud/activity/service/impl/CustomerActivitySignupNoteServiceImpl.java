package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.CustomerActivitySignupNoteMapper;
import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.service.CustomerActivitySignupNoteService;
import com.sida.xiruo.po.common.TableMeta;
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
public class CustomerActivitySignupNoteServiceImpl extends BaseServiceImpl<CustomerActivitySignupNote> implements CustomerActivitySignupNoteService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerActivitySignupNoteServiceImpl.class);
    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + CustomerActivitySignupNoteServiceImpl.class.getName();

    @Autowired
    private DistributedLock distributedLock;

    @Autowired
    private CustomerActivitySignupNoteMapper customerActivitySignupNoteMapper;

    @Override
    public IMybatisDao<CustomerActivitySignupNote> getBaseDao() {
        return customerActivitySignupNoteMapper;
    }


    @Override
    public List<TableMeta> findTableMeta() {
        TableMeta tableMeta = new TableMeta();
        tableMeta.setTableSchema("activity");
        tableMeta.setTableName("customer_activity_signup_note");
        return customerActivitySignupNoteMapper.findTableMeta(tableMeta);
    }

    @Override
    public List<CustomerActivitySignupNote> findVoList(CustomerActivitySignupNote po) {
        return customerActivitySignupNoteMapper.findVoList(po);
    }

    /**
     * 新增和更新操作都需要进行重复检验，因此要进行锁互斥
     * @param po
     * @return
     */
    @Override
    public int insert(CustomerActivitySignupNote po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (customerActivitySignupNoteMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("用户不允许重复报名同一活动");
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
    public int updateByPrimaryKey(CustomerActivitySignupNote po) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new ActivityException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (customerActivitySignupNoteMapper.checkMultiCountByUnique(po) > 0) {
                    throw new ActivityException("用户不允许重复报名同一活动");
                }
                result = super.updateByPrimaryKey(po);
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".updateByPrimaryKey method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
        }

        return result;
    }
}