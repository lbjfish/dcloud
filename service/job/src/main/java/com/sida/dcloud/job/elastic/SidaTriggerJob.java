package com.sida.dcloud.job.elastic;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.sida.dcloud.job.elastic.consumer.ScanAndChangeOrderStatusConsumer;
import com.sida.dcloud.job.po.JobEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class SidaTriggerJob implements SimpleJob {
    private final static Logger LOG = LoggerFactory.getLogger(SidaTriggerJob.class);

    private AbstractJob consumer;

    public SidaTriggerJob(AbstractJob consumer) {
        this.consumer = consumer;
    }

    @Override
    public void execute(ShardingContext shardingContext) {
        JSONObject json = (JSONObject)JSONObject.parse(shardingContext.getJobParameter());
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(json.getString("jobId"));
        jobEntity.setJobName(consumer.getClass().getCanonicalName());
        jobEntity.setShardingTotalCount(json.getInteger("shardingTotalCount"));
        //每一分钟重试
        LocalDateTime datetime = LocalDateTime.now();
        jobEntity.setJobCron(String.format("%s 0/1 * * * ?", datetime.getSecond()));
        consumer.initJob(jobEntity);
    }
}
