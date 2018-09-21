package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivityRelHonoredMapper;
import com.sida.dcloud.activity.po.ActivityRelHonored;
import com.sida.dcloud.activity.service.ActivityRelHonoredService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
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
public class ActivityRelHonoredServiceImpl extends BaseServiceImpl<ActivityRelHonored> implements ActivityRelHonoredService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityRelHonoredServiceImpl.class);

    @Autowired
    private ActivityRelHonoredMapper activityRelHonoredMapper;

    @Override
    public IMybatisDao<ActivityRelHonored> getBaseDao() {
        return activityRelHonoredMapper;
    }

    @Override
    public int deleteByScheduleId(String scheduleId) {
        return activityRelHonoredMapper.deleteByScheduleId(scheduleId);
    }

    @RedisLock
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int saveOrUpdate(List<ActivityRelHonored> list) {
        if(!list.isEmpty()) {
            activityRelHonoredMapper.deleteByScheduleId(list.get(0).getScheduleId());
            return activityRelHonoredMapper.batchInsert(list);
        }
        return -1;
    }
}