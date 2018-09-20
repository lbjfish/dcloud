package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysUserHiddenFieldMapper;
import com.sida.dcloud.auth.po.SysUserHiddenField;
import com.sida.dcloud.auth.service.SysUserHiddenFieldService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserHiddenFieldServiceImpl extends BaseServiceImpl<SysUserHiddenField> implements SysUserHiddenFieldService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserHiddenFieldServiceImpl.class);

    @Autowired
    private SysUserHiddenFieldMapper sysUserHiddenFieldMapper;

    @Override
    public IMybatisDao<SysUserHiddenField> getBaseDao() {
        return sysUserHiddenFieldMapper;
    }
}