package com.sida.dcloud.job.config;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.sida.dcloud.job.util.JobUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ElasticJobEachListener implements com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener {
    @Autowired
    private JobUtil jobUtil;

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {

    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        //任务结束后直接删除
        JSONObject json = (JSONObject) JSONObject.parse(shardingContexts.getJobParameter());
        if(!json.getBoolean("loop")) {
            jobUtil.dropJob(json.getString("jobId"));
        }
    }
}
