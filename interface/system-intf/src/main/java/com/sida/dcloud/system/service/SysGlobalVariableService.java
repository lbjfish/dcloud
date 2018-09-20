package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysGlobalVariable;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.util.List;

public interface SysGlobalVariableService extends IBaseService<SysGlobalVariable> {

    /**
     * 分页获取全局变量 列表
     * @param param
     * @return
     */
    Page<SysGlobalVariable> findPageList(SysGlobalVariable param);
    List<SysGlobalVariable> selectByCondition();
    void loadDatasToRedis();
}