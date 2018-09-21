package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.dao.CustomerFaceDetectionTrackMapper;
import com.sida.dcloud.activity.po.CustomerFaceDetectionTrack;
import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.dcloud.activity.service.CustomerFaceDetectionTrackService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFaceDetectionTrackServiceImpl extends BaseServiceImpl<CustomerFaceDetectionTrack> implements CustomerFaceDetectionTrackService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerFaceDetectionTrackServiceImpl.class);

    @Autowired
    private CustomerFaceDetectionTrackMapper customerFaceDetectionTrackMapper;

    @Override
    public IMybatisDao<CustomerFaceDetectionTrack> getBaseDao() {
        return customerFaceDetectionTrackMapper;
    }

    @Override
    public Page<CustomerFaceDetectionTrack> findPageList(CustomerFaceDetectionTrack po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<CustomerFaceDetectionTrack> list = customerFaceDetectionTrackMapper.findList(po);
        return (Page) list;
    }

    @Override
    public List<CustomerFaceDetectionTrack> findListByActivityId(String activityId) {
        return customerFaceDetectionTrackMapper.findListByActivityId(activityId);
    }

    @Override
    public List<CustomerFaceDetectionTrack> findListByUserId(String userId) {
        return customerFaceDetectionTrackMapper.findListByUserId(userId);
    }
}