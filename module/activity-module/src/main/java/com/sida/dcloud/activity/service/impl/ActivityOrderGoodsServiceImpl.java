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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityOrderGoodsServiceImpl extends BaseServiceImpl<ActivityOrderGoods> implements ActivityOrderGoodsService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityOrderGoodsServiceImpl.class);

    @Autowired
    private ActivityOrderGoodsMapper activityOrderGoodsMapper;

    @Override
    public IMybatisDao<ActivityOrderGoods> getBaseDao() {
        return activityOrderGoodsMapper;
    }

    @Override
    public List<ActivityOrderGoods> findListByOrderId(String orderId) {
        return activityOrderGoodsMapper.findListByOrderId(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int batchInsert(List<ActivityOrderGoods> list) {
        activityOrderGoodsMapper.physicalDeleteByOrderId(list.get(0).getOrderId());
        return activityOrderGoodsMapper.batchInsert(list);
    }
}