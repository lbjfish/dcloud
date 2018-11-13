package com.sida.xiruo.xframework.datasource;


import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import static com.sida.xiruo.xframework.datasource.DynamicDataSourceContextHolder.*;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private final Logger logger = Logger.getLogger(getClass());

    @Override
    protected Object determineCurrentLookupKey() {
//        logger.info("Current DataSource is " + getDataSourceKey());
        return getDataSourceKey();
    }
}