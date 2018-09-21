package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivityGoodsGroupMapper;
import com.sida.dcloud.activity.po.ActivityGoodsGroup;
import com.sida.dcloud.activity.service.ActivityGoodsGroupService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityGoodsGroupServiceImpl extends BaseServiceImpl<ActivityGoodsGroup> implements ActivityGoodsGroupService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityGoodsGroupServiceImpl.class);

    @Autowired
    private ActivityGoodsGroupMapper activityGoodsGroupMapper;

    @Override
    public IMybatisDao<ActivityGoodsGroup> getBaseDao() {
        return activityGoodsGroupMapper;
    }
}