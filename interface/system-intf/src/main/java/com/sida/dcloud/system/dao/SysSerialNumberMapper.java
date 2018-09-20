/**
 * create by huangbaidong
 * @date 2017-03
 */
package com.sida.dcloud.system.dao;


import com.sida.dcloud.system.po.SysSerialNumber;
import com.sida.xiruo.xframework.dao.IMybatisDao;

import java.util.Map;

public interface SysSerialNumberMapper extends IMybatisDao<SysSerialNumber> {

    /**
     * 通过存储过程生成流水号（按日期重置流水号）
     * @param param
     * @return
     */
    Long generateSerialNumberByDate(Map<String, Object> param);

}