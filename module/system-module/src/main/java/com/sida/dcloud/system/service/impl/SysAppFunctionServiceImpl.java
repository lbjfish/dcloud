package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysAppFunctionMapper;
import com.sida.dcloud.system.po.SysAppFunction;
import com.sida.dcloud.system.service.SysAppFunctionService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAppFunctionServiceImpl extends BaseServiceImpl<SysAppFunction> implements SysAppFunctionService {
    private static final Logger logger = LoggerFactory.getLogger(SysAppFunctionServiceImpl.class);

    @Autowired
    private SysAppFunctionMapper sysAppFunctionMapper;

    @Override
    public IMybatisDao<SysAppFunction> getBaseDao() {
        return sysAppFunctionMapper;
    }
}