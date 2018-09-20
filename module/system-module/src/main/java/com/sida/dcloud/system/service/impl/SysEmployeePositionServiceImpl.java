package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysEmployeePositionMapper;
import com.sida.dcloud.auth.po.SysEmployeePosition;
import com.sida.dcloud.system.service.SysEmployeePositionService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysEmployeePositionServiceImpl extends BaseServiceImpl<SysEmployeePosition> implements SysEmployeePositionService {
    private static final Logger logger = LoggerFactory.getLogger(SysEmployeePositionServiceImpl.class);

    @Autowired
    private SysEmployeePositionMapper sysEmployeePositionMapper;

    @Override
    public IMybatisDao<SysEmployeePosition> getBaseDao() {
        return sysEmployeePositionMapper;
    }
}