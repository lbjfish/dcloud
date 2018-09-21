package com.sida.dcloud.activity.service;

import com.sida.dcloud.activity.po.SysUserActivity;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysUserActivityService extends IBaseService<SysUserActivity> {
    int updateUserList(List<SysUser> userList);
    int updateUser(SysUser user);
}