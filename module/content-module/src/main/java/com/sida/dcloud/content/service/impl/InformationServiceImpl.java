package com.sida.dcloud.content.service.impl;

import com.sida.dcloud.content.dao.InformationMapper;
import com.sida.dcloud.content.po.Information;
import com.sida.dcloud.content.service.InformationService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl extends BaseServiceImpl<Information> implements InformationService {
    private static final Logger logger = LoggerFactory.getLogger(InformationServiceImpl.class);

    @Autowired
    private InformationMapper informationMapper;

    @Override
    public IMybatisDao<Information> getBaseDao() {
        return informationMapper;
    }
}