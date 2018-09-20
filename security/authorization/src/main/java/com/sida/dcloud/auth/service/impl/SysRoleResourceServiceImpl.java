package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.service.SysRoleResourceService;
import com.sida.dcloud.auth.dao.SysRoleResourceMapper;
import com.sida.dcloud.auth.po.SysRoleResource;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleResourceServiceImpl extends BaseServiceImpl<SysRoleResource> implements SysRoleResourceService {
    private static final Logger logger = LoggerFactory.getLogger(SysRoleResourceServiceImpl.class);

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Override
    public IMybatisDao<SysRoleResource> getBaseDao() {
        return sysRoleResourceMapper;
    }
}