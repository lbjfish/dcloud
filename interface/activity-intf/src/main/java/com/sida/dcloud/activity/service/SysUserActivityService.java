package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.po.SysUserActivity;
import com.sida.dcloud.activity.vo.SysUserActivityVo;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.xiruo.xframework.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysUserActivityService extends IBaseService<SysUserActivity> {
    int updateUserList(List<SysUser> userList);
    int updateUser(SysUser user);
    Page<SysUserActivityVo> findPageList(SysUserActivity po);

    int inertDto(Map<String, String> map);
    int updateMobile(Map<String, String> map);
    int updateUserInfo(Map<String, String> map);
}