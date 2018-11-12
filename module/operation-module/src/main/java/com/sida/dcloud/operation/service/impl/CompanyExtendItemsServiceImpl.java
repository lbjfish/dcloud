package com.sida.dcloud.operation.service.impl;

import com.sida.dcloud.operation.dao.CompanyExtendItemsMapper;
import com.sida.dcloud.operation.po.CompanyExtendItems;
import com.sida.dcloud.operation.service.CompanyExtendItemsService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyExtendItemsServiceImpl extends BaseServiceImpl<CompanyExtendItems> implements CompanyExtendItemsService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyExtendItemsServiceImpl.class);

    @Autowired
    private CompanyExtendItemsMapper companyExtendItemsMapper;

    @Override
    public IMybatisDao<CompanyExtendItems> getBaseDao() {
        return companyExtendItemsMapper;
    }
}