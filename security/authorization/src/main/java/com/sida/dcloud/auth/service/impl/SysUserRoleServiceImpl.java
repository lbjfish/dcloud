package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.po.SysUserRole;
import com.sida.dcloud.auth.service.SysUserRoleService;
import com.sida.dcloud.auth.dao.SysUserRoleMapper;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements SysUserRoleService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IMybatisDao<SysUserRole> getBaseDao() {
        return sysUserRoleMapper;
    }
}