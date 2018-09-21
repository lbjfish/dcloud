package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivityGoodsRelGroupMapper;
import com.sida.dcloud.activity.po.ActivityGoodsRelGroup;
import com.sida.dcloud.activity.service.ActivityGoodsRelGroupService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityGoodsRelGroupServiceImpl extends BaseServiceImpl<ActivityGoodsRelGroup> implements ActivityGoodsRelGroupService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityGoodsRelGroupServiceImpl.class);

    @Autowired
    private ActivityGoodsRelGroupMapper activityGoodsRelGroupMapper;

    @Override
    public IMybatisDao<ActivityGoodsRelGroup> getBaseDao() {
        return activityGoodsRelGroupMapper;
    }
}