package com.sida.dcloud.job.simple.consumer;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.sida.dcloud.job.client.ActivityClient;
import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.po.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UpdateOrderStatusConsumer extends AbstractConsumer {
    @Autowired
    private ActivityClient activityClient;

    private String orderId;
    private String orderStatus;

    @Override
    public void accept(ShardingContext shardingContext) {
        activityClient.updateOrderStatus(orderId, orderStatus);
    }

    @Override
    public String initJob(JobEntity jobEntity) {
        Optional.ofNullable(jobEntity.getParamMap()).orElseThrow(() -> new JobException("必须提供orderId和orderStatus"));
        Optional.ofNullable(jobEntity.getParamMap().get("orderId")).orElseThrow(() -> new JobException("必须提供orderId"));
        Optional.ofNullable(jobEntity.getParamMap().get("orderStatus")).orElseThrow(() -> new JobException("必须提供orderStatus"));
        orderId = jobEntity.getParamMap().get("orderId");
        orderStatus = jobEntity.getParamMap().get("orderStatus");
        return super.initJob(jobEntity);
    }
}
