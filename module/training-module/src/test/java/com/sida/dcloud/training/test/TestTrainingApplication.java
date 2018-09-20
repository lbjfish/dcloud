package com.sida.dcloud.training.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class TestTrainingApplication {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();

//        config.setMaxActive(100);

        config.setMaxIdle(20);

//        config.setMaxWait(1000l);
        JedisPool pool;
        pool = new JedisPool(config, "2xx.xx.xx.14", 6379);

        boolean borrowOrOprSuccess = true;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            // do redis opt by instance
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                pool.returnBrokenResource(jedis);

        } finally {
            if (borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        jedis = pool.getResource();
    }
}
