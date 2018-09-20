package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.po.SysUserRole;
import com.sida.dcloud.system.dao.SysUserRoleMapper;
import com.sida.dcloud.system.service.SysUserRoleService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements SysUserRoleService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IMybatisDao<SysUserRole> getBaseDao() {
        return sysUserRoleMapper;
    }

    @Override
    public void batchInsert(List<SysUserRole> urList) {
        if (BlankUtil.isNotEmpty(urList)){
            sysUserRoleMapper.batchInsert(urList);
        }
    }

    @Override
    public void insertWithEmployeePosition() {
        sysUserRoleMapper.insertWithEmployeePosition();
    }

    @Override
    public void deleteByUserIds(List<String> userIds) {
        if (BlankUtil.isNotEmpty(userIds)){
            sysUserRoleMapper.deleteByUserIds(userIds);
        }
    }

    @Override
    public void removeAll() {
        sysUserRoleMapper.removeAll();
    }
}