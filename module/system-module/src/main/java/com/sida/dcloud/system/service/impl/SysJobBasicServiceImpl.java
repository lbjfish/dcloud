package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysJobBasicMapper;
import com.sida.dcloud.system.po.SysJobBasic;
import com.sida.dcloud.system.service.SysJobBasicService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysJobBasicServiceImpl extends BaseServiceImpl<SysJobBasic> implements SysJobBasicService {
    private static final Logger logger = LoggerFactory.getLogger(SysJobBasicServiceImpl.class);

    @Autowired
    private SysJobBasicMapper sysJobBasicMapper;

    @Override
    public IMybatisDao<SysJobBasic> getBaseDao() {
        return sysJobBasicMapper;
    }
}