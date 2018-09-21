package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.dao.CustomerPaymentTrackMapper;
import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.dcloud.activity.service.CustomerPaymentTrackService;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerPaymentTrackServiceImpl extends BaseServiceImpl<CustomerPaymentTrack> implements CustomerPaymentTrackService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerPaymentTrackServiceImpl.class);

    @Autowired
    private CustomerPaymentTrackMapper customerPaymentTrackMapper;

    @Override
    public IMybatisDao<CustomerPaymentTrack> getBaseDao() {
        return customerPaymentTrackMapper;
    }

    @Override
    public Page<CustomerPaymentTrack> findPageList(CustomerPaymentTrack po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<CustomerPaymentTrack> list = customerPaymentTrackMapper.findList(po);
        return (Page) list;
    }

    @Override
    public List<CustomerPaymentTrack> findListByActivityId(String activityId) {
        return customerPaymentTrackMapper.findListByActivityId(activityId);
    }

    @Override
    public List<CustomerPaymentTrack> findListByUserId(String userId) {
        return customerPaymentTrackMapper.findListByUserId(userId);
    }
}