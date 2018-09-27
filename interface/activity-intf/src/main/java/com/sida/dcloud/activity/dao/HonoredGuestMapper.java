/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HonoredGuestMapper extends IMybatisDao<HonoredGuest> {
    List<HonoredGuestVo> findVoList(@Param("po")HonoredGuest po);
    int checkMultiCountByUnique(@Param("po")HonoredGuest po);
    int checkRemovableByRel(@Param("ids") String ids);
    List<HonoredGuestVo> findVoListByScheduleId(@Param("scheduleId")String scheduleId);
}