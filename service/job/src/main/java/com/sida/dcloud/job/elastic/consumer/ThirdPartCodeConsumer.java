package com.sida.dcloud.job.elastic.consumer;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.sida.dcloud.job.client.ActivityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ThirdPartCodeConsumer extends AbstractJobConsumer {
    @Autowired
    private ActivityClient activityClient;

    @Override
    public void accept(ShardingContext shardingContext) {
        activityClient.resendThirdPartCode();
    }
}
