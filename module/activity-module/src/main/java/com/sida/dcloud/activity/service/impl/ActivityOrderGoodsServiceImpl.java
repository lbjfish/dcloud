package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivityOrderGoodsMapper;
import com.sida.dcloud.activity.po.ActivityOrderGoods;
import com.sida.dcloud.activity.service.ActivityOrderGoodsService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityOrderGoodsServiceImpl extends BaseServiceImpl<ActivityOrderGoods> implements ActivityOrderGoodsService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityOrderGoodsServiceImpl.class);

    @Autowired
    private ActivityOrderGoodsMapper activityOrderGoodsMapper;

    @Override
    public IMybatisDao<ActivityOrderGoods> getBaseDao() {
        return activityOrderGoodsMapper;
    }
}