package com.sida.dcloud.job.config;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import com.sida.dcloud.job.util.JobUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ElasticJobDistributeListener extends AbstractDistributeOnceElasticJobListener {

    public ElasticJobDistributeListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {

    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {

    }
}
