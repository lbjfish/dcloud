package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysEmployeeOrgMapper;
import com.sida.dcloud.auth.po.SysEmployeeOrg;
import com.sida.dcloud.auth.service.SysEmployeeOrgService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysEmployeeOrgServiceImpl extends BaseServiceImpl<SysEmployeeOrg> implements SysEmployeeOrgService {
    private static final Logger logger = LoggerFactory.getLogger(SysEmployeeOrgServiceImpl.class);

    @Autowired
    private SysEmployeeOrgMapper sysEmployeeOrgMapper;

    @Override
    public IMybatisDao<SysEmployeeOrg> getBaseDao() {
        return sysEmployeeOrgMapper;
    }
}