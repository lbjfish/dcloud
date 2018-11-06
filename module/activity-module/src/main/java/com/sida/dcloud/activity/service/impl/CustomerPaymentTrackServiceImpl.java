package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.dao.CustomerPaymentTrackMapper;
import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.dcloud.activity.service.CustomerPaymentTrackService;
import com.sida.dcloud.activity.util.pay.PayUtilWithXcx;
import com.sida.dcloud.activity.vo.CustomerPaymentTrackVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerPaymentTrackServiceImpl extends BaseServiceImpl<CustomerPaymentTrack> implements CustomerPaymentTrackService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerPaymentTrackServiceImpl.class);

    @Autowired
    private CustomerPaymentTrackMapper customerPaymentTrackMapper;
    @Autowired
    private PayUtilWithXcx payUtilWithXcx;

    @Override
    public IMybatisDao<CustomerPaymentTrack> getBaseDao() {
        return customerPaymentTrackMapper;
    }

    @Override
    public Page<CustomerPaymentTrackVo> findPageList(CustomerPaymentTrack po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<CustomerPaymentTrackVo> list = customerPaymentTrackMapper.findList(po);
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

    @Override
    public CustomerPaymentTrack findOneByOrderNo(String orderNo) {
        return customerPaymentTrackMapper.findOneByOrderNo(orderNo);
    }

    @Override
    public CustomerPaymentTrack findOneByNoteId(String noteId) {
        return customerPaymentTrackMapper.findOneByNoteId(noteId);
    }

    @Override
    public CustomerPaymentTrack findOneByTransactionId(String transactionId) {
        return customerPaymentTrackMapper.findOneByTransactionId(transactionId);
    }

    @Override
    public CustomerPaymentTrack findOneByTrackId(String trackId) {
        return customerPaymentTrackMapper.selectByPrimaryKey(trackId);
    }

    @Override
    public int scanAndChangePayStatusWithXcx() {
        // 查询未得到反馈&&未查询状态的日志
        List<String> idList = customerPaymentTrackMapper.selectIdsByUnknownStatus();
        idList.forEach(id -> {
            payUtilWithXcx.orderQuery(id);
            try {
                //200毫秒
                TimeUnit.MILLISECONDS.sleep(200);
            } catch(InterruptedException e) {
                LOG.error("休眠时发生异常: ", e);
            }
        });
        return 0;
    }
}