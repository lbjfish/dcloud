package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysButtonMapper;
import com.sida.dcloud.auth.service.SysButtonService;
import com.sida.dcloud.auth.po.SysButton;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysButtonServiceImpl extends BaseServiceImpl<SysButton> implements SysButtonService {
    private static final Logger logger = LoggerFactory.getLogger(SysButtonServiceImpl.class);

    @Autowired
    private SysButtonMapper sysButtonMapper;

    @Override
    public IMybatisDao<SysButton> getBaseDao() {
        return sysButtonMapper;
    }
}