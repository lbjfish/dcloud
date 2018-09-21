package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivityScheduleMapper;
import com.sida.dcloud.activity.po.ActivitySchedule;
import com.sida.dcloud.activity.service.ActivityScheduleService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityScheduleServiceImpl extends BaseServiceImpl<ActivitySchedule> implements ActivityScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityScheduleServiceImpl.class);

    @Autowired
    private ActivityScheduleMapper activityScheduleMapper;

    @Override
    public IMybatisDao<ActivitySchedule> getBaseDao() {
        return activityScheduleMapper;
    }
}