package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysEmployeeOrg;
import com.sida.dcloud.auth.po.SysOrg;
import com.sida.xiruo.xframework.dao.IMybatisDao;

import java.util.List;

public interface SysEmployeeOrgMapper extends IMybatisDao<SysEmployeeOrg> {
    List<SysOrg> findDeptsByEmpId(String empId);
}