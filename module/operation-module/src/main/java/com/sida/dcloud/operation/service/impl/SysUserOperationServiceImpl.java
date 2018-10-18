package com.sida.dcloud.operation.service.impl;

import com.sida.dcloud.operation.client.ActivityClient;
import com.sida.dcloud.operation.client.ContentClient;
import com.sida.dcloud.operation.client.SystemClient;
import com.sida.dcloud.operation.common.OperationConstants;
import com.sida.dcloud.operation.common.OperationException;
import com.sida.dcloud.operation.dao.SysUserOperationMapper;
import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.SysUserOperation;
import com.sida.dcloud.operation.service.CommonUserService;
import com.sida.dcloud.operation.service.SysUserOperationService;
import com.sida.dcloud.operation.util.SMSUtil;
import com.sida.xiruo.common.util.MD5Util;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.DistributedLock;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.MobileUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private static final String LOCK_KEY_CHECK_MULTI = "LOCK_KEY_CHECK_MULTI_" + SysUserOperationServiceImpl.class.getName();

    @Autowired
    private DistributedLock distributedLock;

    @Autowired
    private SysUserOperationMapper sysUserOperationMapper;
    @Autowired
    private SMSUtil smsUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonUserService commonUserService;

    //feign
    @Autowired
    private SystemClient systemClient;
    @Autowired
    private ActivityClient activityClient;
    @Autowired
    private ContentClient contentClient;

//    @Value("${common.sms.validity}}")
    private long validity;

    @Override
    public IMybatisDao<SysUserOperation> getBaseDao() {
        return sysUserOperationMapper;
    }

    @Override
    public String generateMobileVerifyCode(String mobileType, String mobile) {
        String verifyCode = MobileUtil.generateAuthCode();
        if (validity == 0){
            validity = (long)10*60*1000;//若无，默认为10分钟有效时间
        }
        /**
         * 发送验证码
         */
        smsUtil.SMSSendMessageByHuaweiCloud(mobile, OperationConstants.SMS_TEMPLATE_VERIFYCODE, new String[] {verifyCode});


        /**
         * 保存手机与验证码对应关系，同时绑定有效时间
         */
        redisUtil.putInMap(RedisKey.SHORT_MSG_AUTH_CODE,mobileType + ":" + mobile, verifyCode, System.currentTimeMillis() + validity);
//        System.out.println(redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE,mobileType + ":" + mobile));
//        System.out.println(mobileType + ":" + mobile);
        return verifyCode;
    }

    @Override
    public CommonUserOperation verifyBindStatus(CommonUserOperation dto) {
        return sysUserOperationMapper.verifyBindStatus(dto);
    }

    /**
     *
     * @param dto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int bindMobile(CommonUserOperation dto) {
        try {
            String verifyCode = (String)redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE, dto.getMobileType() + ":" + dto.getMobile());
            Optional.ofNullable(verifyCode).orElseThrow(() -> new OperationException("验证码已过期"));
            if(!verifyCode.equals(dto.getVerifyCode())) {
                throw new OperationException("输入的验证码不正确");
            }
            dto.setPassword(MD5Util.MD5PWD(dto.getPassword()));
            int result = insertUserWithMobile(dto);
            //移除验证码
            redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE, dto.getMobileType() + ":" + dto.getMobile());
            return 0;
        } catch(Exception e) {
            throw new OperationException(e);
        }
    }

    /**
     * todo 分布式事务待处理
     * @param dto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    int insertUserWithMobile(CommonUserOperation dto) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new OperationException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (sysUserOperationMapper.checkMultiCountByUnique(dto) > 0) {
                    throw new OperationException("同一手机只能注册一次，请登录");
                }

                result = sysUserOperationMapper.saveOrUpdateDto(dto);
                Map<String, String> dtoMap = BeanUtils.describe(dto);
                systemClient.saveOrUpdateDto(dtoMap);
                activityClient.insertDto(dtoMap);
                contentClient.insertDto(dtoMap);
                commonUserService.saveOrUpdateDto(dto);

            } catch(Exception e) {
                LOG.error(getClass().getName() + ".inertUserWithMobile method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
            return result;
        }
    }

    /**
     *
     * @param dto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int bindNewMobile(CommonUserOperation dto) {
        try {
            String verifyCode = (String)redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE, dto.getMobileType() + ":" + dto.getMobile());
            Optional.ofNullable(verifyCode).orElseThrow(() -> new OperationException("验证码已过期"));
            if(!verifyCode.equals(dto.getVerifyCode())) {
                throw new OperationException("输入的验证码不正确");
            }
            int result = updateUserWithMobile(dto);
            //移除验证码
            redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE, dto.getMobileType() + ":" + dto.getMobile());
            return result;
        } catch(Exception e) {
            throw new OperationException(e);
        }
    }

    /**
     * todo 分布式事务待处理
     * @param dto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    int updateUserWithMobile(CommonUserOperation dto) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = -1;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new OperationException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (sysUserOperationMapper.checkMultiCountByUnique(dto) > 0) {
                    throw new OperationException("同一手机只能注册一次，请登录");
                }

                result = sysUserOperationMapper.updateMobile(dto);
                Map<String, String> dtoMap = BeanUtils.describe(dto);
                systemClient.updateMobile(dtoMap);
                activityClient.updateMobile(dtoMap);
                contentClient.updateMobile(dtoMap);

            } catch(Exception e) {
                LOG.error(getClass().getName() + ".inertUserWithMobile method occured exception", e);
            } finally {
                boolean releaseResult = distributedLock.releaseLock(LOCK_KEY_CHECK_MULTI);
                LOG.debug("Release lock : " + LOCK_KEY_CHECK_MULTI + (releaseResult ? " success" : " failed"));
            }
            return result;
        }
    }

    /**
     * todo 分布式事务待处理
     * @param dto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateUserInfo(CommonUserOperation dto) {
        try {
            int result = sysUserOperationMapper.updateUserInfo(dto);
            commonUserService.updateUserInfo(dto);
            Map<String, String> dtoMap = BeanUtils.describe(dto);
            systemClient.updateUserInfo(dtoMap);
            activityClient.updateUserInfo(dtoMap);
            contentClient.updateUserInfo(dtoMap);
            return result;
        } catch(Exception e) {
            throw new OperationException(e);
        }
    }

    /**
     * todo 分布式事务待处理
     * @param dto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateUserPassword(CommonUserOperation dto) {
        try {
            dto.setPassword(MD5Util.MD5PWD(dto.getPassword()));
            int result = sysUserOperationMapper.updateUserPassword(dto);
            systemClient.updateUserPassword(BeanUtils.describe(dto));
            return result;
        } catch(Exception e) {
            throw new OperationException(e);
        }
    }
}