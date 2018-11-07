package com.sida.dcloud.activity.util;

import com.sida.dcloud.activity.client.JobClient;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.xdomain.common.SpringBeansManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class Bootstrap implements CommandLineRunner, ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    private JobClient jobClient;

    /**
     * setApplicationContext方法在run方法前被调用
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeansManager.getInstance().setApplicationContext(applicationContext);
    }

    /**
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
//        char[] array = new char[100];
//        Arrays.fill(array, '*');
//        LOG.info("run method: {}", new String(array));
        SpringBeansManager.getInstance().init();
        initSchedulerJob();
    }

    /**
     * 初始化计划任务
     */
    private void initSchedulerJob() {
//        JobEntity jobEntity = new JobEntity();
//        List<JobEntity> jobList = (List<JobEntity>)jobClient.query(jobEntity);
    }
}
