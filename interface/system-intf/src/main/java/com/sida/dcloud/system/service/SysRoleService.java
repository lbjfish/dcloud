package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysPosition;
import com.sida.dcloud.auth.po.SysRole;
import com.sida.dcloud.auth.vo.RoleDTO;
import com.sida.dcloud.auth.vo.SysRoleVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

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
     * 检验角色code非空且唯一
     * @param code
     */
    void checkCode(String code);

    /**
     * 根据编码获取角色
     * @param code
     * @return
     */
    SysRole selectByCode(String code);

    /**
     * 获取角色
     * @param roleCodes
     * @return
     */
    Map<String,SysRole> findRoleByCodes(List<String> roleCodes);

    /**
     * 物理删除所有
     */
    void removeAll();

    /**
     * 批量插入
     * @param roleList
     */
    void batchInsert(List<SysRole> roleList);

    /**
     * 全量同步岗位数据至对应角色
     */
    void insertWithPositions();

    /**
     * 全量同步岗位数据至对应角色
     */
    void insertWithPositions(List<SysPosition> list);

    /**
     * 根据ids获取角色集合
     * @param list
     * @return
     */
    Map<String,SysRole> findMapByIds(List<String> list);

    /**
     * 批量更新
     * @param list
     */
    void updateWithPositions(List<SysPosition> list);

    /**
     * 根据用户id获取角色信息
     * @param userId
     * @return
     */
    List<RoleDTO> findRoleListByUserId(String userId);

    /**
     * 检查员工是否属于某一角色范围
     * @param empId
     * @param roleScope
     * @return
     */
    boolean checkIfEmpLikeRoleScope(String empId, String roleScope);

    /**
     * 检查员工是否属于某一角色范围
     * @param empId
     * @param roles
     * @return
     */
    boolean checkIfEmpInRoleScope(String empId, List<String> roles);
}