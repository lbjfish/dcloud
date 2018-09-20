package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysEmployeeOrg;
import com.sida.dcloud.auth.po.SysOrg;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysEmployeeOrgService extends IBaseService<SysEmployeeOrg> {
    /**
     * 获取指定用户所属部门
     * @param userId
     * @return
     */
    List<SysOrg> findDeptsByEmpId(String userId);
}