package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysUserHiddenField;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysUserHiddenFieldService extends IBaseService<SysUserHiddenField> {

    /**
     * 批量插入隐藏列配置信息
     * @param userId
     * @param pageCode
     * @param hiddenFields
     * @return
     */
    int insertMany(String userId, String pageCode, List<SysUserHiddenField> hiddenFields);

    /**
     * 根据userId 与 前端页面id 删除设置的隐藏列
     * @param userId
     * @param pageCode
     * @return
     */
    int deleteByUserAndPageCode(String userId, String pageCode);
}