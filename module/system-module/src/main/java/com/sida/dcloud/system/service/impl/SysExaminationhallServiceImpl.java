package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysExaminationhallMapper;
import com.sida.dcloud.system.po.SysExaminationhall;
import com.sida.dcloud.system.service.SysExaminationhallService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysExaminationhallServiceImpl extends BaseServiceImpl<SysExaminationhall> implements SysExaminationhallService {
    private static final Logger logger = LoggerFactory.getLogger(SysExaminationhallServiceImpl.class);

    @Autowired
    private SysExaminationhallMapper sysExaminationhallMapper;

    @Override
    public IMybatisDao<SysExaminationhall> getBaseDao() {
        return sysExaminationhallMapper;
    }
}