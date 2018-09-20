package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysUserRole;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysUserRoleService extends IBaseService<SysUserRole> {
    /**
     * 批量插入
     * @param urList
     */
    void batchInsert(List<SysUserRole> urList);

    /**
     * 全量更新
     */
    void insertWithEmployeePosition();

    /**
     * 根据用户ids删除关联关系
     * @param userIds
     */
    void deleteByUserIds(List<String> userIds);

    /**
     * 物理删除所有
     */
    void removeAll();
}