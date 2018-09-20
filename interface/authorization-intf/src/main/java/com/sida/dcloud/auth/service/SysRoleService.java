package com.sida.dcloud.auth.service;

import com.sida.dcloud.auth.po.SysRole;
import com.sida.dcloud.auth.vo.RoleDTO;
import com.sida.dcloud.auth.vo.SysRoleVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.util.List;

public interface SysRoleService extends IBaseService<SysRole> {
    List<SysRole> selectRolesByUserId(String userId);

    /**
     * 批量启用角色
     * @param roleIds
     */
    void enableRoles(String roleIds);

    /**
     * 批量停用角色
     * @param roleIds
     */
    void disableRoles(String roleIds);

    /**
     * 获取角色列表
     * @param role
     * @return
     */
    Page<SysRole> findPageList(SysRole role);

    /**
     * 获取角色vo对象
     * @param roleId
     * @return
     */
    SysRoleVo findOneVo(String roleId);

    /**
     * 保存角色信息
     * @param param
     * @return
     */
    int saveRole(SysRoleVo param);

    /**
     * 根据用户id获取角色信息
     * @param userId
     * @return
     */
    List<RoleDTO> findRoleListByUserId(String userId);
}