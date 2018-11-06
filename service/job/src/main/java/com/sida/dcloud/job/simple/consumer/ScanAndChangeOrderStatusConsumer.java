package com.sida.dcloud.job.simple.consumer;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.sida.dcloud.job.client.ActivityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ScanAndChangeOrderStatusConsumer extends AbstractConsumer {
    @Autowired
    private ActivityClient activityClient;

    @Override
    public void accept(ShardingContext shardingContext) {
        activityClient.scanAndChangeOrderStatus();
    }
}
