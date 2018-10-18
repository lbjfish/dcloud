package com.sida.dcloud.operation.service.impl;

import com.sida.dcloud.operation.dao.CommonUserMapper;
import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.CommonUser;
import com.sida.dcloud.operation.service.CommonUserService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommonUserServiceImpl extends BaseServiceImpl<CommonUser> implements CommonUserService {
    private static final Logger LOG = LoggerFactory.getLogger(CommonUserServiceImpl.class);

    @Autowired
    private CommonUserMapper commonUserMapper;

    @Override
    public IMybatisDao<CommonUser> getBaseDao() {
        return commonUserMapper;
    }

    @Override
    public Map<String, String> selectByPrimaryKeyToAuth(String id) {
        return commonUserMapper.selectByPrimaryKeyToAuth(id);
    }

    @Override
    public int saveOrUpdateDto(CommonUserOperation dto) {
        return commonUserMapper.saveOrUpdateDto(dto);
    }

    @Override
    public int updateUserInfo(CommonUserOperation dto) {
        return commonUserMapper.updateUserInfo(dto);
    }
}