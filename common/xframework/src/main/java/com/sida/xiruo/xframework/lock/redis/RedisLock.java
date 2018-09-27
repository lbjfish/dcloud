package com.sida.xiruo.xframework.lock.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author
 * @date 2017年6月14日 下午3:10:36
 * @version 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisLock {
    long KEEP_MILLS = 30000;
    long SLEEP_MILLS = 200;
    int RETRY_TIMES = 5;

    /** 锁的资源，redis的key*/
    String value() default "default";

    /** 持锁时间,单位毫秒*/
    long keepMills() default KEEP_MILLS;

    /** 当获取失败时候动作*/
    LockFailAction action() default LockFailAction.CONTINUE;

    enum LockFailAction{
        /** 放弃 */
        GIVEUP,
        /** 继续 */
        CONTINUE;
    }

    /** 重试的间隔时间,设置GIVEUP忽略此项*/
    long sleepMills() default SLEEP_MILLS;

    /** 重试次数*/
    int retryTimes() default RETRY_TIMES;
}
