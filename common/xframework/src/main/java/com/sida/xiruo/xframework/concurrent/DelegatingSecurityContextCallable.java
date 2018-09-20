package com.sida.xiruo.xframework.concurrent;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;

/**
 * 委托原始callable任务，使其绑定一个SecurityContext
 *
 * @author Tung
 * @version 1.0
 * @date 2018/2/23.
 * @update
 */

public final class DelegatingSecurityContextCallable<V> implements Callable<V> {

    private final Callable<V> delegate;

    /**
     * 代理的Callable运行时所使用的SecurityContext
     */
    private final SecurityContext delegateSecurityContext;

    /**
     * 原始的SecurityContext。
     */
    private SecurityContext originalSecurityContext;

    /**
     * 通过一个指定的SecurityContext创建一个DelegatingSecurityContextCallable实例.
     * @param delegate 代理的Callable, 不能为null
     * @param securityContext Runnable所使用的securityContext实例。不能为null.
     */
    public DelegatingSecurityContextCallable(Callable<V> delegate,
                                             SecurityContext securityContext) {
        Assert.notNull(delegate, "delegate cannot be null");
        Assert.notNull(securityContext, "securityContext cannot be null");
        this.delegate = delegate;
        this.delegateSecurityContext = securityContext;
    }

    @Override
    public V call() throws Exception {
        this.originalSecurityContext = SecurityContextHolder.getContext();

        try {
            SecurityContextHolder.setContext(delegateSecurityContext);
            return delegate.call();
        }
        finally {
            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            if(emptyContext.equals(originalSecurityContext)) {
                SecurityContextHolder.clearContext();
            } else {
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
     * 创建DelegatingSecurityContextCallable的工厂方法。
     *
     * @param delegate 原始的Callable实例  不能为null.
     * @param securityContext Callable所需的securityContext实例
     * @return
     */
    public static <V> Callable<V> create(Callable<V> delegate, SecurityContext securityContext) {
        Assert.notNull(delegate, "delegate cannot be null");
        Assert.notNull(securityContext, "securityContext cannot be null");
        return new DelegatingSecurityContextCallable<V>(delegate, securityContext);
    }
}
