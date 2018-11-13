package com.sida.dcloud.operation.service.impl;

import com.sida.dcloud.operation.dao.DesignerHonorMapper;
import com.sida.dcloud.operation.po.DesignerHonor;
import com.sida.dcloud.operation.service.DesignerHonorService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignerHonorServiceImpl extends BaseServiceImpl<DesignerHonor> implements DesignerHonorService {
    private static final Logger logger = LoggerFactory.getLogger(DesignerHonorServiceImpl.class);

    @Autowired
    private DesignerHonorMapper designerHonorMapper;

    @Override
    public IMybatisDao<DesignerHonor> getBaseDao() {
        return designerHonorMapper;
    }
}