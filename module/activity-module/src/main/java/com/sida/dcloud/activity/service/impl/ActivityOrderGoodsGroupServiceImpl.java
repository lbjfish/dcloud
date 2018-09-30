package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivityOrderGoodsGroupMapper;
import com.sida.dcloud.activity.po.ActivityOrderGoodsGroup;
import com.sida.dcloud.activity.service.ActivityOrderGoodsGroupService;
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
public class ActivityOrderGoodsGroupServiceImpl extends BaseServiceImpl<ActivityOrderGoodsGroup> implements ActivityOrderGoodsGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityOrderGoodsGroupServiceImpl.class);

    @Autowired
    private ActivityOrderGoodsGroupMapper activityOrderGoodsGroupMapper;

    @Override
    public IMybatisDao<ActivityOrderGoodsGroup> getBaseDao() {
        return activityOrderGoodsGroupMapper;
    }

    @Override
    public List<ActivityOrderGoodsGroup> findListByOrderId(String orderId) {
        return activityOrderGoodsGroupMapper.findListByOrderId(orderId);
    }

    @Override
    public List<ActivityOrderGoodsGroup> findListByIds(String ids) {
        return activityOrderGoodsGroupMapper.findListByIds(ids);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int batchInsert(List<ActivityOrderGoodsGroup> list) {
        activityOrderGoodsGroupMapper.physicalDeleteByOrderId(list.get(0).getOrderId());
        return activityOrderGoodsGroupMapper.batchInsert(list);
    }
}