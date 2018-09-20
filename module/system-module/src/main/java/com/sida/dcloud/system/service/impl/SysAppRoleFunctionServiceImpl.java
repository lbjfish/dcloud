package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysAppRoleFunctionMapper;
import com.sida.dcloud.system.po.SysAppRoleFunction;
import com.sida.dcloud.system.service.SysAppRoleFunctionService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAppRoleFunctionServiceImpl extends BaseServiceImpl<SysAppRoleFunction> implements SysAppRoleFunctionService {
    private static final Logger logger = LoggerFactory.getLogger(SysAppRoleFunctionServiceImpl.class);

    @Autowired
    private SysAppRoleFunctionMapper sysAppRoleFunctionMapper;

    @Override
    public IMybatisDao<SysAppRoleFunction> getBaseDao() {
        return sysAppRoleFunctionMapper;
    }
}