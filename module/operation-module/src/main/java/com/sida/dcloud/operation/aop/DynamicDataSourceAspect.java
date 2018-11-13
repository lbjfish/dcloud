package com.sida.dcloud.operation.aop;

import com.sida.xiruo.xframework.datasource.AbstractDynamicDataSourceAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect extends AbstractDynamicDataSourceAspect {
    @Pointcut("execution( * com.sida.dcloud.operation.dao.*.*(..))")
    public void daoAspect() {
    }
}