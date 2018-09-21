/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.CustomerFaceDetectionTrack;
import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerFaceDetectionTrackMapper extends IMybatisDao<CustomerFaceDetectionTrack> {
    List<CustomerFaceDetectionTrack> findList(@Param("po") CustomerFaceDetectionTrack po);
    List<CustomerFaceDetectionTrack> findListByActivityId(@Param("activityId")String activityId);
    List<CustomerFaceDetectionTrack> findListByUserId(@Param("userId")String userId);
}