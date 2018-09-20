package com.sida.dcloud.content.util;

import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ContentCacheUtil implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ContentCacheUtil.class);


    @Autowired
    private RedisUtil redisUtil;



    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @Override
    public void run(String... strings) throws Exception {
//        char[] array = new char[100];
//        Arrays.fill(array, '*');
//        LOG.info(new String(array));

    }

}
