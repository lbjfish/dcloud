package com.sida.xiruo.xframework.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDynamicDataSourceAspect {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String[] getQueryPrefix() {
        return new String[] {"select","find","check","business"};
    }

    protected abstract void daoAspect();

    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
        if (isQueryMethod) {
            DynamicDataSourceContextHolder.useSlaveDataSource();
            logger.info("Switch DataSource to [{}] in Method [{}]",
                    DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
        }
    }

    @After("daoAspect())")
    public void restoreDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        logger.info("Restore DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }

    private Boolean isQueryMethod(String methodName) {
        for (String prefix : getQueryPrefix()) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}