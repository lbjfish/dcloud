package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysShortMessageMapper;
import com.sida.dcloud.system.po.SysShortMessage;
import com.sida.dcloud.system.service.SysShortMessageService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysShortMessageServiceImpl extends BaseServiceImpl<SysShortMessage> implements SysShortMessageService {
    private static final Logger logger = LoggerFactory.getLogger(SysShortMessageServiceImpl.class);

    @Autowired
    private SysShortMessageMapper sysShortMessageMapper;

    @Override
    public IMybatisDao<SysShortMessage> getBaseDao() {
        return sysShortMessageMapper;
    }
}