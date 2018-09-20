package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysFieldMapper;
import com.sida.dcloud.auth.po.SysField;
import com.sida.dcloud.auth.service.SysFieldService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysFieldServiceImpl extends BaseServiceImpl<SysField> implements SysFieldService {
    private static final Logger logger = LoggerFactory.getLogger(SysFieldServiceImpl.class);

    @Autowired
    private SysFieldMapper sysFieldMapper;

    @Override
    public IMybatisDao<SysField> getBaseDao() {
        return sysFieldMapper;
    }
}