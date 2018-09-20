package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysField;
import com.sida.dcloud.auth.po.SysUserHiddenField;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysFieldService extends IBaseService<SysField> {
    /**
     * 获取字段资源
     * @param pageCode
     * @param nameList
     * @param codeList
     * @return
     */
    List<SysUserHiddenField> findFields(String userId, String pageCode, List<String> nameList, List<String> codeList);
}