package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.po.SysUserActivity;
import com.sida.dcloud.activity.vo.SysUserActivityVo;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysUserActivityService extends IBaseService<SysUserActivity> {
    int updateUserList(List<SysUser> userList);
    int updateUser(SysUser user);
    Page<SysUserActivityVo> findPageList(SysUserActivity po);
}