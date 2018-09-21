package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivityOrderMapper;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.service.ActivityOrderService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityOrderServiceImpl extends BaseServiceImpl<ActivityOrder> implements ActivityOrderService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityOrderServiceImpl.class);

    @Autowired
    private ActivityOrderMapper activityOrderMapper;

    @Override
    public IMybatisDao<ActivityOrder> getBaseDao() {
        return activityOrderMapper;
    }
}