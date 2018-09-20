package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysAppFunctionGroup;
import com.sida.dcloud.system.vo.SysAppFunctionGroupVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysAppFunctionGroupService extends IBaseService<SysAppFunctionGroup> {
    /**
     * 获取角色对应的app功能列表
     * @param appId  应用id
     * @param roleIds 角色id集合
     * @return
     */
    public  List<SysAppFunctionGroupVo>  findSysAppFunctionGroupList(String appId,List<String> roleIds);
}