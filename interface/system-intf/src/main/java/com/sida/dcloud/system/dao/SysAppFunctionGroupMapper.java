/**
 * create by Administrator
 * @date 2018-02
 */
package com.sida.dcloud.system.dao;

import com.sida.dcloud.system.po.SysAppFunctionGroup;
import com.sida.dcloud.system.vo.SysAppFunctionGroupVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysAppFunctionGroupMapper extends IMybatisDao<SysAppFunctionGroup> {
    /**
     * 获取角色对应的功能区
     * @param appId
     * @param roleIds
     * @return
     */
    public List<Map<String,Object>> finSysAppFunctionGroupList(@Param("appId") String appId, @Param("roleIds") List<String> roleIds);
}