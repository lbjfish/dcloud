package com.sida.dcloud.activity.client;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.job.po.JobEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JobClientHystrix implements JobClient {
    private static final Logger LOG = LoggerFactory.getLogger(JobClientHystrix.class);

    @Override
    public Object query(JobEntity jobEntity) {
        LOG.warn("进入断路器-query。。。");
        throw new ActivityException("query 查询失败.");
    }

    @Override
    public Object createJobWithOrderStatus(JobEntity jobEntity) {
        LOG.warn("进入断路器-createJobWithOrderStatus。。。");
//        throw new ActivityException("createJobWithOrderStatus 创建订单状态任务失败.");
        return -1;
    }

    @Override
    public Object dropJobWithOrderStatus(String jobName) {
        LOG.warn("进入断路器-dropJobWithOrderStatus。。。");
//        throw new ActivityException("dropJobWithOrderStatus 停止订单状态任务失败.");
        return -1;
    }
}
