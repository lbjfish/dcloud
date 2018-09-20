/**
 * create by hbd
 * @date 2018-02
 */
package com.sida.dcloud.system.dao;

import com.sida.dcloud.system.po.SysAccessLogDetail;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAccessLogDetailMapper extends IMybatisDao<SysAccessLogDetail> {

    //分页查询时间段内日志
    List<SysAccessLogDetail> selecAccessLogstByDate(@Param("startTime")String startTime,@Param("endTime")String endTime);
}