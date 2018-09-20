package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysPosition;
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

    List<SysRole> selectByCode(@Param("code")String code);

    List<SysRole> selectByCodes(@Param("codes")List<String> codes);

    /**
     * 物理删除所有
     */
    void removeAll();

    /**
     * 批量插入
     * @param list
     */
    void batchInsert(@Param(value = "list") List<SysRole> list);

    /**
     * 全量同步岗位数据至对应角色
     */
    void insertWithPositions();

    /**
     * 增量同步岗位数据至对应角色
     * @param list
     */
    void insertWithPositionList(@Param(value = "list") List<SysPosition> list);

    /**
     * 根据ids获取角色集合
     * @param ids
     * @return
     */
    List<SysRole> findByIds(@Param(value = "ids") List<String> ids);

    /**
     * 批量更新
     * @param list
     */
    void updateWithPositions(@Param(value = "list") List<SysPosition> list);

    /**
     * 根据用户id获取角色信息
     * @param userId
     * @return
     */
    List<RoleDTO> findRoleListByUserId(@Param(value = "userId") String userId);

    /**
     * 检查员工是否属于某一角色范围
     * @param empId
     * @param roles
     * @return
     */
    int checkIfEmpInRoleScope(@Param(value = "empId")String empId, @Param(value = "roles")List<String> roles);

    /**
     * 检查员工是否属于某一角色范围
     * @param empId
     * @param roleScope
     * @return
     */
    int checkIfEmpLikeRoleScope(@Param(value = "empId")String empId, @Param(value = "roleScope")String roleScope);
}