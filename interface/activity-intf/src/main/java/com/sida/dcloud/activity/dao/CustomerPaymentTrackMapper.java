/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerPaymentTrackMapper extends IMybatisDao<CustomerPaymentTrack> {
    List<CustomerPaymentTrack> findList(@Param("po") CustomerPaymentTrack po);
    List<CustomerPaymentTrack> findListByActivityId(@Param("activityId")String activityId);
    List<CustomerPaymentTrack> findListByUserId(@Param("userId")String userId);
}