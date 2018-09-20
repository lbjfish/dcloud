package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.service.AuthcodeService;
import com.sida.xiruo.common.util.AuthCodeConstants;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.MobileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 验证码实现类
 * created by wuzhenwei
 */
@Service
public class AuthcodeServiceImpl implements AuthcodeService {

    private static final Logger logger = LoggerFactory.getLogger(SysAppFunctionServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SMSMessageServiceImpl sms;

    @Override
    public boolean isValid(String code, String mobile, String reqType) {
        if (BlankUtil.isNotEmpty(code) && BlankUtil.isNotEmpty(mobile) && BlankUtil.isNotEmpty(reqType)) {
            Object validCode = null;
            switch (reqType) {
                case AuthCodeConstants.REQTYPE_REMOTE_REGISTER_1:
                    //redis验证
                    validCode = redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE, RedisKey.REMOTE_REGISTER + mobile);
                    if (BlankUtil.isNotEmpty(validCode) && code.equals(validCode.toString())) {
                        // 失效验证码
                        redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE, RedisKey.REMOTE_REGISTER + mobile);
                        return true;
                    }
                    break;
                case AuthCodeConstants.REQTYPE_REMOTE_LOGIN_2:
                    //redis验证
                    validCode = redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE, RedisKey.REMOTE_LOGIN + mobile);
                    if (BlankUtil.isNotEmpty(validCode) && code.equals(validCode.toString())) {
                        // 失效验证码
                        redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE, RedisKey.REMOTE_LOGIN + mobile);
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    @Override
    public void getRemoteAuthCode(String mobile, String reqType) {
        //校验手机号码格式
        if (BlankUtil.isEmpty(mobile)) {
            throw new ServiceException("手机号码为空！");
        }else if (mobile.trim().length() != 11){
            throw new ServiceException("手机号码长度异常！");
        }

        //生成6位短信验证码
        String[] codes = {MobileUtil.generateAuthCode()};

        //校验业务类型
        if (BlankUtil.isEmpty(reqType)) {
            throw new ServiceException("业务类型为空！");
        }else if (reqType.equals(AuthCodeConstants.REQTYPE_REMOTE_REGISTER_1)){
            //远程教育平台-注册
            sendMessage(mobile, AuthCodeConstants.TEMPLE_ID,codes,RedisKey.REMOTE_REGISTER,RedisKey.REMOTE_REGISTER_VALIDITY);
        }else if (reqType.equals(AuthCodeConstants.REQTYPE_REMOTE_LOGIN_2)){
            //远程教育平台-登录
            sendMessage(mobile, AuthCodeConstants.TEMPLE_ID,codes,RedisKey.REMOTE_LOGIN,RedisKey.REMOTE_LOGIN_VALIDITY);
        }else {
            throw new ServiceException("未定义的业务类型！");
        }

    }

    @Override
    public void sendMessage(String mobile, String templateId, String[] datas, String mobileType, Long validity) {
        if (validity == null){
            validity = (long)30*60*1000;//若无，默认为30分钟有效时间
        }
        /**
         * 发送验证码
         */
        sms.SMSSendMessage(mobile, templateId, datas);

        /**
         * 保存手机与验证码对应关系，同时绑定有效时间
         */
        redisUtil.putInMap(RedisKey.SHORT_MSG_AUTH_CODE,mobileType+mobile,datas[0]);
        redisUtil.putInMap(RedisKey.SHORT_MSG_AUTH_CODE_VALIDITY,mobileType+mobile,System.currentTimeMillis()+validity);
    }

    @Override
    public void cleanAuthcodeRedis() {
        /**
         * 获取所有需要失效的短信验证码集合
         * 现有：1.《远程教育平台》短信验证码-注册；2.《远程教育平台》短信验证码-登录；
         */
        //1.获取验证码集合
        Map<String,String> map = redisUtil.getEntriesFromMap(RedisKey.SHORT_MSG_AUTH_CODE_VALIDITY);

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();

            //2.比对当前时间戳 与 到期时间时间戳。若过期则移除
            long current = System.currentTimeMillis();
            try {
                if (current > Long.valueOf(entry.getValue().toString())){
                    redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE,entry.getKey().toString());
                    redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE_VALIDITY,entry.getKey().toString());
                }
            }catch (Exception e){
                System.out.println("error value for type long");
            }
        }
    }
}
