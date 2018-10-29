package com.sida.dcloud.operation.service.impl;

//import com.codingapi.tx.annotation.TxTransaction;
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
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.MobileUtil;
import com.sida.xiruo.xframework.util.UUIDGenerate;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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

    public String generateMobileVerifyCode(String mobileType, String mobile) {
        String verifyCode = MobileUtil.generateAuthCode();
        if (validity == 0){
            validity = 5 * 60 * 1000;//若无，默认为5分钟有效时间
        }
        /**
         * 保存手机与验证码对应关系，同时绑定有效时间
         */
        redisUtil.putInMap(RedisKey.SHORT_MSG_AUTH_CODE,mobileType + ":" + mobile, verifyCode, (System.currentTimeMillis() + validity) / 1000);
//        System.out.println(redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE,mobileType + ":" + mobile));
//        System.out.println(mobileType + ":" + mobile);
        return verifyCode;
    }

    @Override
    public String generateMobileVerifyCode(String mobileType, String mobile, int templateId) {
        String verifyCode = generateMobileVerifyCode(mobileType, mobile);
        /**
         * 发送验证码
         */
        try {
            smsUtil.SMSSendMessage(mobile, templateId, new String[]{verifyCode});
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationException(e);
        }
        return verifyCode;
    }

    @Override
    public CommonUserOperation verifyBindStatus(CommonUserOperation dto) {
        return sysUserOperationMapper.verifyBindStatus(dto);
    }


    @Override
    public CommonUserOperation findThirdPartAccount(CommonUserOperation dto) {
        List<CommonUserOperation> list = sysUserOperationMapper.findThirdPartAccount(dto);
        if(!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     *
     * @param dto
     * @return
     */
//    @TxTransaction(isStart=true)
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int bindThirdPartAccount(CommonUserOperation dto) {
        try {
            int result = 0;
            boolean isCommonUser = false;
            boolean isSysUser = false;
            if(BlankUtil.isNotEmpty(dto.getWechat()) || BlankUtil.isNotEmpty(dto.getQq())) {
                isSysUser = true;
            }
            if(BlankUtil.isNotEmpty(dto.getAlipayNo())
                || BlankUtil.isNotEmpty(dto.getSinaWeibo())
                || BlankUtil.isNotEmpty(dto.getFacebookNo())
                || BlankUtil.isNotEmpty(dto.getTwitterNo())) {
                isCommonUser = true;
            }
            if(isCommonUser) {
                result = commonUserService.bindThirdPartAccount(dto);
            }
            if(isSysUser) {
                result = sysUserOperationMapper.bindThirdPartAccount(dto);
                Map<String, String> dtoMap = BeanUtils.describe(dto);
                systemClient.bindThirdPartAccount(dtoMap);
                activityClient.bindThirdPartAccount(dtoMap);
                contentClient.bindThirdPartAccount(dtoMap);
            }

            return result;
        } catch(Exception e) {
            throw new OperationException(e);
        }
    }

    /**
     *
     * @param dto
     * @return
     */
//    @TxTransaction(isStart=true)
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int unbindThirdPartAccount(CommonUserOperation dto) {
        try {
            int result = 0;
            boolean isCommonUser = false;
            boolean isSysUser = false;
            if(",1,3,".indexOf(String.format(",%s,", dto.getLoginFrom())) >= 0) {
                isSysUser = true;
            }
            if(",2,4,5,6,".indexOf(String.format(",%s,", dto.getLoginFrom())) >= 0) {
                isCommonUser = true;
            }
            if(isCommonUser) {
                result = commonUserService.unbindThirdPartAccount(dto.getLoginFrom(), dto.getMobile());
            }
            if(isSysUser) {
                result = sysUserOperationMapper.unbindThirdPartAccount(dto.getLoginFrom(), dto.getMobile());
                systemClient.unbindThirdPartAccount(dto.getLoginFrom(), dto.getMobile());
                activityClient.unbindThirdPartAccount(dto.getLoginFrom(), dto.getMobile());
                contentClient.unbindThirdPartAccount(dto.getLoginFrom(), dto.getMobile());
            }

            return result;
        } catch(Exception e) {
            throw new OperationException(e);
        }
    }

    /**
     *
     * @param dto
     * @return
     */
//    @TxTransaction(isStart=true)
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int bindMobile(CommonUserOperation dto) {
        try {
            String verifyCode = (String)redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE, dto.getMobileType() + ":" + dto.getMobile());
            Optional.ofNullable(verifyCode).orElseThrow(() -> new OperationException("验证码已过期"));
            if(!verifyCode.equals(dto.getVerifyCode())) {
                throw new OperationException("输入的验证码不正确");
            }
            //移除验证码
            redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE, dto.getMobileType() + ":" + dto.getMobile());
            int result = insertUserWithMobile(dto);
            return result;
        } catch(Exception e) {
            throw new OperationException(e);
        }
    }

    /**
     *
     * @param dto
     * @return
     */
//    @TxTransaction
    @Transactional(propagation = Propagation.REQUIRED)
    int insertUserWithMobile(CommonUserOperation dto) {
        boolean lock = distributedLock.lock(LOCK_KEY_CHECK_MULTI, RedisLock.KEEP_MILLS, RedisLock.RETRY_TIMES, RedisLock.SLEEP_MILLS);
        int result = 0;
        if(!lock) {
            LOG.debug("Get lock failed : " + LOCK_KEY_CHECK_MULTI);
            throw new OperationException("获取锁失败，请稍后重试。。。");
        } else {
            LOG.debug("Get lock success : " + LOCK_KEY_CHECK_MULTI);
            try {
                if (sysUserOperationMapper.checkMultiCountByUnique(dto) == 0) {
//                    String password = Optional.ofNullable(dto.getPassword()).orElse(MobileUtil.generateAuthCode());
                    String password = dto.getMobile();
                    dto.setPassword(MD5Util.MD5PWD(password));
                    dto.setId(UUIDGenerate.getNextId());
                    result = sysUserOperationMapper.saveOrUpdateDto(dto);
                    Map<String, String> dtoMap = BeanUtils.describe(dto);
                    commonUserService.saveOrUpdateDto(dto);
                    activityClient.insertDto(dtoMap);
                    systemClient.saveOrUpdateDto(dtoMap);
                    contentClient.insertDto(dtoMap);
                }
            } catch(Exception e) {
                LOG.error(getClass().getName() + ".inertUserWithMobile method occured exception", e);
                throw new OperationException(e);
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
//    @TxTransaction(isStart=true)
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
     *
     * @param dto
     * @return
     */
//    @TxTransaction
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
     *
     * @param dto
     * @return
     */
//    @TxTransaction(isStart=true)
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
     *
     * @param dto
     * @return
     */
//    @TxTransaction(isStart=true)
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateUserPassword(CommonUserOperation dto) {
        try {
            String verifyCode = (String)redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE, dto.getMobileType() + ":" + dto.getMobile());
            Optional.ofNullable(verifyCode).orElseThrow(() -> new OperationException("验证码已过期"));
            if(!verifyCode.equals(dto.getVerifyCode())) {
                throw new OperationException("输入的验证码不正确");
            }
            //移除验证码
            redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE, dto.getMobileType() + ":" + dto.getMobile());
            dto.setPassword(MD5Util.MD5PWD(dto.getPassword()));
            int result = sysUserOperationMapper.updateUserPassword(dto);
            systemClient.updateUserPassword(BeanUtils.describe(dto));
            return result;
        } catch(Exception e) {
            throw new OperationException(e);
        }
    }

    /**
     * 测试分布式事务
     * @return
     */
//    @TxTransaction(isStart=true)
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int testDistributeTransaction(String id, String remark) {
        String s = System.currentTimeMillis() + "";
        sysUserOperationMapper.testDistributeTransaction("100", s);
        commonUserService.testDistributeTransaction("100", s);
        systemClient.testDistributeTransaction("100", s);
        activityClient.testDistributeTransaction("100", s);
        contentClient.testDistributeTransaction("100", s);
        if("0".equals(id)) {
            int x = 100 / 0;
        }
        return 0;
    }
}