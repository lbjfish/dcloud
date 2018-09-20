package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.system.dao.SysEmployeeOrgMapper;
import com.sida.dcloud.auth.po.SysEmployeeOrg;
import com.sida.dcloud.system.service.SysEmployeeOrgService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysEmployeeOrgServiceImpl extends BaseServiceImpl<SysEmployeeOrg> implements SysEmployeeOrgService {
    private static final Logger logger = LoggerFactory.getLogger(SysEmployeeOrgServiceImpl.class);

    @Autowired
    private SysEmployeeOrgMapper sysEmployeeOrgMapper;

    @Override
    public IMybatisDao<SysEmployeeOrg> getBaseDao() {
        return sysEmployeeOrgMapper;
    }

    @Override
    public List<SysOrg> findDeptsByEmpId(String empId) {
        return sysEmployeeOrgMapper.findDeptsByEmpId(empId);
    }
}