package com.sida.dcloud.operation.service.impl;

import com.sida.dcloud.operation.dao.CompanyCooperationBrandMapper;
import com.sida.dcloud.operation.po.CompanyCooperationBrand;
import com.sida.dcloud.operation.service.CompanyCooperationBrandService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyCooperationBrandServiceImpl extends BaseServiceImpl<CompanyCooperationBrand> implements CompanyCooperationBrandService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyCooperationBrandServiceImpl.class);

    @Autowired
    private CompanyCooperationBrandMapper companyCooperationBrandMapper;

    @Override
    public IMybatisDao<CompanyCooperationBrand> getBaseDao() {
        return companyCooperationBrandMapper;
    }
}