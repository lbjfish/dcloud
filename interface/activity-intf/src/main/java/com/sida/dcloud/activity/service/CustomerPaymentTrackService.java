package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface CustomerPaymentTrackService extends IBaseService<CustomerPaymentTrack> {
    Page<CustomerPaymentTrack> findPageList(CustomerPaymentTrack po);
    List<CustomerPaymentTrack> findListByActivityId(String activityId);
    List<CustomerPaymentTrack> findListByUserId(String userId);
}