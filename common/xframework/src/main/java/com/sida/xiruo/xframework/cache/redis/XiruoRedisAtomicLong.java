package com.sida.xiruo.xframework.cache.redis;

import com.sida.xiruo.common.util.Xiruo;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.sida.xiruo.common.util.Xiruo.TIMEPOINT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XiruoRedisAtomicLong extends RedisAtomicLong {
    private static final Logger LOG = LoggerFactory.getLogger(XiruoRedisAtomicLong.class);

    public XiruoRedisAtomicLong(String redisCounter, RedisConnectionFactory factory, TIMEPOINT timepoint) {
        super(redisCounter, factory);
        Optional.ofNullable(timepoint).orElseThrow(() -> new RuntimeException("Timepoint cannot null"));
        Long increment = get();
        if (null == increment || increment.longValue() == 0) {//初始设置过期时间
            expire(Xiruo.getLiveTime(timepoint), TimeUnit.SECONDS);
        }
    }

    public XiruoRedisAtomicLong(String redisCounter, RedisConnectionFactory  factory, long timeout) {
        super(redisCounter, factory);
        Long increment = get();

        if ((null == increment || increment.longValue() == 0) && timeout > 0) {//初始设置过期时间
            expire(timeout, TimeUnit.SECONDS);
        }
    }
}
