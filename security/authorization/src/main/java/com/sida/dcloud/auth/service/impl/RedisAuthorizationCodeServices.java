package com.sida.dcloud.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.JedisCluster;

/**
 * 将authenticationCode存放到redis中。
 *
 * @author Tung
 * @version 1.0
 * @date 2017/12/28.
 * @update
 */

public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
    private static final Logger LOG = LoggerFactory.getLogger(RedisAuthorizationCodeServices.class);
    private JedisCluster jedisCluster;
    /**
     * expired in 5mins default.
     */
    private int expiredTime = 5*60;

    public RedisAuthorizationCodeServices(JedisCluster jedisCluster) {
        if (jedisCluster == null) {
            throw new NullPointerException("Jedis Cluster for RedisAuthorizationCodeServices can not be null");
        }
        this.jedisCluster = jedisCluster;
    }

    /**
     * in seconds
     * @param times
     * @return
     */
    public AuthorizationCodeServices withExpriedTime(int times) {
        expiredTime = times;
        return this;
    }

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        LOG.debug("Store Code In Redis: " + code);
        jedisCluster.setex(("OAuth_CODE_" + code).getBytes(), expiredTime, SerializationUtils.serialize(authentication));
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        LOG.debug("Remove Code In Redis: " + code);
        byte[] keyBytes = ("OAuth_CODE_" + code).getBytes();
        OAuth2Authentication auth = (OAuth2Authentication) SerializationUtils.deserialize(jedisCluster.get(keyBytes));
        jedisCluster.del(keyBytes);
        return auth;
    }
}
