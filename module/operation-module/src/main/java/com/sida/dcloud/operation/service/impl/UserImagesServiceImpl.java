package com.sida.dcloud.operation.service.impl;

import com.sida.dcloud.operation.dao.UserImagesMapper;
import com.sida.dcloud.operation.po.UserImages;
import com.sida.dcloud.operation.service.UserImagesService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImagesServiceImpl extends BaseServiceImpl<UserImages> implements UserImagesService {
    private static final Logger logger = LoggerFactory.getLogger(UserImagesServiceImpl.class);

    @Autowired
    private UserImagesMapper userImagesMapper;

    @Override
    public IMybatisDao<UserImages> getBaseDao() {
        return userImagesMapper;
    }
}