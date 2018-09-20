package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysInfoChangeLogMapper;
import com.sida.dcloud.auth.po.SysInfoChangeLog;
import com.sida.dcloud.auth.service.SysInfoChangeLogService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysInfoChangeLogServiceImpl extends BaseServiceImpl<SysInfoChangeLog> implements SysInfoChangeLogService {
    private static final Logger logger = LoggerFactory.getLogger(SysInfoChangeLogServiceImpl.class);

    @Autowired
    private SysInfoChangeLogMapper sysInfoChangeLogMapper;

    @Override
    public IMybatisDao<SysInfoChangeLog> getBaseDao() {
        return sysInfoChangeLogMapper;
    }
}