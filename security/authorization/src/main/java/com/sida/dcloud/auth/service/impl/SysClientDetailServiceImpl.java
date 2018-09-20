package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysClientDetailMapper;
import com.sida.dcloud.auth.po.SysClientDetail;
import com.sida.dcloud.auth.service.SysClientDetailService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysClientDetailServiceImpl extends BaseServiceImpl<SysClientDetail> implements SysClientDetailService {
    private static final Logger logger = LoggerFactory.getLogger(SysClientDetailServiceImpl.class);

    @Autowired
    private SysClientDetailMapper sysClientDetailMapper;

    @Override
    public IMybatisDao<SysClientDetail> getBaseDao() {
        return sysClientDetailMapper;
    }
}