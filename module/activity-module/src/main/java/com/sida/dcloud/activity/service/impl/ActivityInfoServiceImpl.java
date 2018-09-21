package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivityInfoMapper;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.service.ActivityInfoService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityInfoServiceImpl extends BaseServiceImpl<ActivityInfo> implements ActivityInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInfoServiceImpl.class);

    @Autowired
    private ActivityInfoMapper activityInfoMapper;

    @Override
    public IMybatisDao<ActivityInfo> getBaseDao() {
        return activityInfoMapper;
    }
}