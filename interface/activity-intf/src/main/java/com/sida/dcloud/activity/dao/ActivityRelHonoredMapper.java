/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.po.ActivityRelHonored;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityRelHonoredMapper extends IMybatisDao<ActivityRelHonored> {
    int deleteByScheduleId(@Param("scheduleId") String scheduleId);
    int batchInsert(@Param("list") List<ActivityRelHonored> list);
}