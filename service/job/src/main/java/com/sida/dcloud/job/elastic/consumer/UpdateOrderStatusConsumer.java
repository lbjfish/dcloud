package com.sida.dcloud.job.elastic.consumer;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.sida.dcloud.job.client.ActivityClient;
import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.po.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UpdateOrderStatusConsumer extends AbstractJobConsumer {
    @Autowired
    private ActivityClient activityClient;

    @Override
    public void accept(ShardingContext shardingContext) {
        JSONObject json = (JSONObject)JSONObject.parse(shardingContext.getJobParameter());
        activityClient.updateOrderStatus(json.getString("orderId"), json.getString("orderStatus"));
    }

    @Override
    public String initJob(JobEntity jobEntity) {
        Optional.ofNullable(jobEntity.getParamMap()).orElseThrow(() -> new JobException("必须提供orderId和orderStatus"));
        Optional.ofNullable(jobEntity.getParamMap().get("orderId")).orElseThrow(() -> new JobException("必须提供orderId"));
        Optional.ofNullable(jobEntity.getParamMap().get("orderStatus")).orElseThrow(() -> new JobException("必须提供orderStatus"));
        return super.initJob(jobEntity);
    }
}
