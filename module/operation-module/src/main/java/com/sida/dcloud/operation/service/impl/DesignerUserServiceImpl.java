package com.sida.dcloud.operation.service.impl;

import com.sida.dcloud.operation.dao.DesignerUserMapper;
import com.sida.dcloud.operation.po.DesignerUser;
import com.sida.dcloud.operation.service.DesignerUserService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignerUserServiceImpl extends BaseServiceImpl<DesignerUser> implements DesignerUserService {
    private static final Logger logger = LoggerFactory.getLogger(DesignerUserServiceImpl.class);

    @Autowired
    private DesignerUserMapper designerUserMapper;

    @Override
    public IMybatisDao<DesignerUser> getBaseDao() {
        return designerUserMapper;
    }
}