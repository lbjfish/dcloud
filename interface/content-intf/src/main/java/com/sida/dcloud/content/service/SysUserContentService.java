package com.sida.dcloud.content.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.content.po.SysUserContent;
import com.sida.dcloud.content.vo.SysUserContentVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface SysUserContentService extends IBaseService<SysUserContent> {
    int updateUserList(List<SysUser> userList);
    int updateUser(SysUser user);
    Page<SysUserContentVo> findPageList(SysUserContent po);

    int inertDto(Map<String, String> map);
    int updateMobile(Map<String, String> map);
    int updateUserInfo(Map<String, String> map);
}