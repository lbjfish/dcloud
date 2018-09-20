package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysAttachFileMapper;
import com.sida.dcloud.system.po.SysAttachFile;
import com.sida.dcloud.system.service.SysAttachFileService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAttachFileServiceImpl extends BaseServiceImpl<SysAttachFile> implements SysAttachFileService {
    private static final Logger logger = LoggerFactory.getLogger(SysAttachFileServiceImpl.class);

    @Autowired
    private SysAttachFileMapper sysAttachFileMapper;

    @Override
    public IMybatisDao<SysAttachFile> getBaseDao() {
        return sysAttachFileMapper;
    }
}