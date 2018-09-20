package com.sida.xiruo.xframework.lock;

// DistributedLock.java 顶级接口
/**
 * @author
 * @date 2017年6月14日 下午3:11:05
 * @version 1.0.0
 */
public interface DistributedLock {
    long TIMEOUT_MILLIS = 30000;

    int RETRY_TIMES = Integer.MAX_VALUE;

    long SLEEP_MILLIS = 500;

    boolean lock(String key);

    boolean lock(String key, int retryTimes);

    boolean lock(String key, int retryTimes, long sleepMillis);

    boolean lock(String key, long expire);

    boolean lock(String key, long expire, int retryTimes);

    boolean lock(String key, long expire, int retryTimes, long sleepMillis);

    boolean releaseLock(String key);
}
