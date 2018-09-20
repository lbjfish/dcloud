package com.sida.xiruo.xframework.concurrent;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * 委托一个Runnable，以实现在执行线程下，可以获取到发起者的SecurityContext.
 *
 * @author Tung
 * @version 1.0
 * @date 2018/2/23.
 * @update
 */
public final class DelegatingSecurityContextRunnable implements Runnable {

    private final Runnable delegate;

    /**
     * 代理的Runnable运行时所使用的SecurityContext
     */
    private final SecurityContext delegateSecurityContext;

    /**
     * 原始的SecurityContext。
     */
    private SecurityContext originalSecurityContext;

    /**
     * 通过一个指定的SecurityContext创建一个DelegatingSecurityContextRunnable实例.
     * @param delegate 代理的Runnable, 不能为null
     * @param securityContext Runnable所使用的securityContext实例。不能为null.
     */
    public DelegatingSecurityContextRunnable(Runnable delegate,
                                             SecurityContext securityContext) {
        Assert.notNull(delegate, "delegate cannot be null");
        Assert.notNull(securityContext, "securityContext cannot be null");
        this.delegate = delegate;
        this.delegateSecurityContext = securityContext;
    }

    @Override
    public void run() {
        this.originalSecurityContext = SecurityContextHolder.getContext();

        try {
            // 设置securityContext
            SecurityContextHolder.setContext(delegateSecurityContext);
            delegate.run();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            if(emptyContext.equals(originalSecurityContext)) {
                // 清除securityContext
                SecurityContextHolder.clearContext();
            } else {
                // 还原securityContext
                SecurityContextHolder.setContext(originalSecurityContext);
            }
            this.originalSecurityContext = null;
        }
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    /**
     * 创建DelegatingSecurityContextRunnable的工厂方法。
     *
     * @param delegate 原始的Runnable实例  不能为null.
     * @param securityContext Runnable所需的securityContext实例
     * @return
     */
    public static Runnable create(Runnable delegate, SecurityContext securityContext) {
        Assert.notNull(delegate, "delegate cannot be  null");
        Assert.notNull(securityContext, "securityContext cannot be null");
        return new DelegatingSecurityContextRunnable(delegate, securityContext);
    }
}
