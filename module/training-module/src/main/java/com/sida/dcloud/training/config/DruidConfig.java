package com.sida.dcloud.training.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.sida.xiruo.xframework.datasource.DataSourceKey;
import com.sida.xiruo.xframework.datasource.DynamicDataSource;
import com.sida.xiruo.xframework.datasource.DynamicDataSourceContextHolder;
import com.sida.xiruo.xframework.util.DESUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class DruidConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static Lock lock = new ReentrantLock();
    private DataSource dataSource;

    @Value("${spring.datasource.master.url}")
    private String dbUrl;

    @Value("${spring.datasource.master.username}")
    private String username;

    @Value("${spring.datasource.master.password}")
    private String password;

    @Value("${spring.datasource.business.url}")
    private String dbUrlBusiness;

    @Value("${spring.datasource.business.username}")
    private String usernameBusiness;

    @Value("${spring.datasource.business.password}")
    private String passwordBusiness;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.filters}")
    private String filters;

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("loginUsername", "druid");
        reg.addInitParameter("loginPassword", "123");
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
        filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
        return filterRegistrationBean;
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource() throws SQLException {
        if(dataSource == null) {
            lock.lock();
            if(dataSource == null) {
                DynamicDataSource dynamicDataSource = new DynamicDataSource();
                Map<Object, Object> dataSourceMap = new HashMap<>();
                DataSource masterDs = createMasterDataSource();
                DataSource businessDs = createBusinessDataSource();
                dataSourceMap.put(DataSourceKey.master.name(), masterDs);
                dataSourceMap.put(DataSourceKey.businuess.name(), businessDs);

                // 将 master 数据源作为默认指定的数据源
                dynamicDataSource.setDefaultTargetDataSource(masterDs);
                // 将 master 和 slave 数据源作为指定的数据源
                dynamicDataSource.setTargetDataSources(dataSourceMap);

                // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
                DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

                // 将 Slave 数据源的 key 放在集合中，用于轮循
                DynamicDataSourceContextHolder.slaveDataSourceKeys.addAll(dataSourceMap.keySet());
                DynamicDataSourceContextHolder.slaveDataSourceKeys.remove(DataSourceKey.master.name());
                dataSource = dynamicDataSource;
            }
            lock.unlock();
        }
        return dataSource;
    }

    private DataSource createMasterDataSource() throws SQLException {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        logger.warn("--------------------------------master: "+dbUrl);
        datasource.setUsername(DESUtils.getDecryptString(username));
        datasource.setPassword(DESUtils.getDecryptString(password));
        fillDataSource(datasource);
        return datasource;
    }

    private DataSource createBusinessDataSource() throws SQLException {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrlBusiness);
        logger.warn("--------------------------------business: "+dbUrlBusiness);
        datasource.setUsername(DESUtils.getDecryptString(usernameBusiness));
        datasource.setPassword(DESUtils.getDecryptString(passwordBusiness));
        fillDataSource(datasource);
        return datasource;
    }

    private void fillDataSource(DruidDataSource datasource) throws SQLException {
        datasource.setDriverClassName(driverClassName);
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setFilters(filters);
    }

    /**
     * 配置 SqlSessionFactoryBean
     * @ConfigurationProperties 在这里是为了将 MyBatis 的 mapper 位置和持久层接口的别名设置到
     * Bean 的属性中，如果没有使用 *.xml 则可以不用该配置，否则将会产生 invalid bond statement 异常
     *
     * @return the sql session factory bean
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws SQLException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }

    /**
     * 注入 DataSourceTransactionManager 用于事务管理
     */
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}