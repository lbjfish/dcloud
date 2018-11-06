package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.CustomerPaymentTrackVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface CustomerPaymentTrackService extends IBaseService<CustomerPaymentTrack> {
    Page<CustomerPaymentTrackVo> findPageList(CustomerPaymentTrack po);
    List<CustomerPaymentTrack> findListByActivityId(String activityId);
    List<CustomerPaymentTrack> findListByUserId(String userId);
    CustomerPaymentTrack findOneByOrderNo(String orderNo);
    CustomerPaymentTrack findOneByNoteId(String noteId);
    CustomerPaymentTrack findOneByTransactionId(String transactionId);
    CustomerPaymentTrack findOneByTrackId(String trackId);
    int scanAndChangePayStatusWithXcx();
}