/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.ActivityOrderVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ActivityOrderMapper extends IMybatisDao<ActivityOrder> {
    List<ActivityOrderVo> findVoList(@Param("po") ActivityOrder po);
    int checkRemovableByRel(@Param("ids") String ids);
    int checkCountForActivityOrderStatus(@Param("ids") String ids, @Param("activityOrderStatus") String activityOrderStatus);
    int updateActivityOrderStatus(@Param("orderId")String orderId, @Param("activityOrderStatus")String activityOrderStatus);
    int updateActivityOrderStatusByNoteId(@Param("noteId")String noteId, @Param("activityOrderStatus")String activityOrderStatus, @Param("date") String date);
    String getCurrentOrderNo();
    ActivityOrder findOneByOrderNo(@Param("orderNo")String orderNo);
    ActivityOrder findOneByNoteId(@Param("noteId")String noteId);
}