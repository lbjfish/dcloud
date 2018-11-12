package com.sida.dcloud.operation.service.impl;

import com.sida.dcloud.operation.dao.CompanyImagesMapper;
import com.sida.dcloud.operation.po.CompanyImages;
import com.sida.dcloud.operation.service.CompanyImagesService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyImagesServiceImpl extends BaseServiceImpl<CompanyImages> implements CompanyImagesService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyImagesServiceImpl.class);

    @Autowired
    private CompanyImagesMapper companyImagesMapper;

    @Override
    public IMybatisDao<CompanyImages> getBaseDao() {
        return companyImagesMapper;
    }
}