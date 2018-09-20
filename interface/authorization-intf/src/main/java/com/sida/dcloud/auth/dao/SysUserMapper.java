package com.sida.dcloud.auth.dao;

import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.dcloud.auth.vo.UserInfo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysUserMapper extends IMybatisDao<SysUser> {
    SysUser selectUserByName(String username);
    List<SysUser> selectAll();

    /**
     * 获取用户拓展信息列表（包含角色信息：角色id、角色name）
     * @param condition
     * @return
     */
    List<SysUserVo> selectVoList(SysUser condition);

    /**
     * 建立用户-角色关联关系
     * @param userId
     * @param roleId
     * @return
     */
    int addUserRoleRela(@Param("userId") String userId, @Param("roleId") String roleId);

    /**
     * 删除员工关联的角色id
     * @param userId
     */
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserInfo getUserInfo(@Param("userId") String userId);

    SysUser selectUserByFaceId(@Param("faceId") String faceId);
    SysUser selectUserByPhone(@Param("phone") String phone);
}