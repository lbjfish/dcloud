package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysTrainingfieldModelsMapper;
import com.sida.dcloud.system.po.SysTrainingfieldModels;
import com.sida.dcloud.system.service.SysTrainingfieldModelsService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysTrainingfieldModelsServiceImpl extends BaseServiceImpl<SysTrainingfieldModels> implements SysTrainingfieldModelsService {
    private static final Logger logger = LoggerFactory.getLogger(SysTrainingfieldModelsServiceImpl.class);

    @Autowired
    private SysTrainingfieldModelsMapper sysTrainingfieldModelsMapper;

    @Override
    public IMybatisDao<SysTrainingfieldModels> getBaseDao() {
        return sysTrainingfieldModelsMapper;
    }
}