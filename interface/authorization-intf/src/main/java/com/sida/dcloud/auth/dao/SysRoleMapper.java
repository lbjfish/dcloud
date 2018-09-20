package com.sida.dcloud.auth.dao;

import com.sida.dcloud.auth.po.SysRole;
import com.sida.dcloud.auth.vo.RoleDTO;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends IMybatisDao<SysRole> {
    List<SysRole> selectRolesByUserId(String userId);

    /**
     * 批量启用角色
     * @param roleIds
     */
    void enableRoles(@Param("roleIds") String roleIds);

    /**
     * 批量停用角色
     * @param roleIds
     */
    void disableRoles(@Param("roleIds") String roleIds);

    /**
     * 查询角色列表
     * @param sysRole
     * @return
     */
    List<SysRole> findList(SysRole sysRole);

    /**
     * 根据用户id获取角色信息
     * @param userId
     * @return
     */
    List<RoleDTO> findRoleListByUserId(@Param(value = "userId") String userId);
}