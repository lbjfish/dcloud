package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.CustomerFaceDetectionTrack;
import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface CustomerFaceDetectionTrackService extends IBaseService<CustomerFaceDetectionTrack> {
    Page<CustomerFaceDetectionTrack> findPageList(CustomerFaceDetectionTrack po);
    List<CustomerFaceDetectionTrack> findListByActivityId(String activityId);
    List<CustomerFaceDetectionTrack> findListByUserId(String userId);
}