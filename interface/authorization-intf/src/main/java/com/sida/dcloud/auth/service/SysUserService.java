package com.sida.dcloud.auth.service;

import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.OrderUserDTO;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.dcloud.auth.vo.UserInfo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface SysUserService extends IBaseService<SysUser> {
    /**
     * 通过faceId查询用户
     * @param faceId
     * @return
     */
    UserDetails selectUserByFaceId(String faceId);


    /**
     * 通过帐号（手机号）查询
     * @return
     */
    UserDetails selectUserByPhone(String phone);


    SysUser selectUserByName(String name);

    /**
     * 条件查询用户列表
     * @param param
     * @return
     */
    Page<SysUserVo> findPageList(SysUser param);

    /**
     * 新增或编辑用户，并建立 用户-角色 关联关系
     * @param param
     */
    int saveOrUpdateUser(SysUserVo param);

    /**
     * 获取登录用户信息：不包含前端资源信息
     * @param userId
     * @param resFlag
     * @return
     */
    UserInfo getUserInfo(String userId, Boolean resFlag);

    /**
     * 验证码验证接口（一次校验接口：校验成功后会失效该验证码
     * @param code 验证码
     * @param mobile  手机号
     * @param reqType   业务类型
     */
    boolean isValid(String code, String mobile, String reqType);



}