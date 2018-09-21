/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.dao;

import com.sida.dcloud.activity.po.SysUserActivity;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

public interface SysUserActivityMapper extends IMybatisDao<SysUserActivity> {
    int updateByUserPrimaryKey(@Param("po") SysUser po);
}