package com.sida.dcloud.operation.service.impl;

import com.sida.dcloud.operation.client.SystemClient;
import com.sida.dcloud.operation.common.OperationConstants;
import com.sida.dcloud.operation.common.OperationException;
import com.sida.dcloud.operation.dao.SysUserOperationMapper;
import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.SysUserOperation;
import com.sida.dcloud.operation.service.CommonUserService;
import com.sida.dcloud.operation.service.SysUserOperationService;
import com.sida.dcloud.operation.util.SMSUtil;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.MobileUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SysUserOperationServiceImpl extends BaseServiceImpl<SysUserOperation> implements SysUserOperationService {
    private static final Logger LOG = LoggerFactory.getLogger(SysUserOperationServiceImpl.class);

    @Autowired
    private SysUserOperationMapper sysUserOperationMapper;
    @Autowired
    private SMSUtil smsUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonUserService commonUserService;
    @Autowired
    private SystemClient systemClient;

    @Override
    public IMybatisDao<SysUserOperation> getBaseDao() {
        return sysUserOperationMapper;
    }

    @Override
    public String generateMobileVerifyCode(String mobile) {
        String verifyCode = MobileUtil.generateAuthCode();
        smsUtil.SMSSendMessageByHuaweiCloud(mobile, OperationConstants.SMS_TEMPLATE_VERIFYCODE, new String[] {verifyCode});
        return verifyCode;
    }

    @Override
    public CommonUserOperation verifyBindStatus(String loginFrom, String account) {
        return sysUserOperationMapper.verifyBindStatus(loginFrom, account);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int bindMobile(CommonUserOperation dto) {
        try {
            Map<String, Object> map = new HashMap<>();
            BeanUtils.populate(dto, map);
            systemClient.saveOrUpdateDto(map);
            commonUserService.saveOrUpdateDto(dto);
            int result = sysUserOperationMapper.saveOrUpdateDto(dto);
            return result;
        } catch(Exception e) {
            throw new OperationException(e);
        }
    }
}