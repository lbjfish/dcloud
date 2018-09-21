package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivityGoodsMapper;
import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.service.ActivityGoodsService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityGoodsServiceImpl extends BaseServiceImpl<ActivityGoods> implements ActivityGoodsService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityGoodsServiceImpl.class);

    @Autowired
    private ActivityGoodsMapper activityGoodsMapper;

    @Override
    public IMybatisDao<ActivityGoods> getBaseDao() {
        return activityGoodsMapper;
    }
}