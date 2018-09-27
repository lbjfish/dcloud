/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivitySchedule;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.ActivityScheduleVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityScheduleMapper extends IMybatisDao<ActivitySchedule> {
    List<ActivityScheduleVo> findVoList(@Param("po")ActivitySchedule po);
    int checkMultiCountByUnique(@Param("po") ActivitySchedule po);
    int checkRemovableByRel(@Param("ids") String ids);
    List<ActivityScheduleVo> findVoListByHonoredGuestId(@Param("honoredGuestId")String honoredGuestId);
}