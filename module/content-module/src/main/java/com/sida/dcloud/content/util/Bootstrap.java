package com.sida.dcloud.content.util;

import com.sida.dcloud.xdomain.common.SpringBeansManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class Bootstrap implements CommandLineRunner, ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * setApplicationContext方法在run方法前被调用
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
//        char[] array = new char[100];
//        Arrays.fill(array, '*');
//        LOG.info("run method: {}", new String(array));
        SpringBeansManager.getInstance().init();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        char[] array = new char[100];
//        Arrays.fill(array, '*');
//        LOG.info("setApplicationContext method: {}", new String(array));
        SpringBeansManager.getInstance().setApplicationContext(applicationContext);
    }
}
