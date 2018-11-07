package com.sida.dcloud.job.elastic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//@ElasticSimpleJob(cron="* 0/1 * * * ?",jobName="test123",shardingTotalCount=1,jobParameter="测试参数",shardingItemParameters="0=A,1=B")
//@Component
public class MyJob implements SimpleJob {
    private final static Logger LOG = LoggerFactory.getLogger(MyJob.class);
    @Autowired
    private RedisUtil redisUtil;
    public MyJob() {
        LOG.info("开始...");
    }
    @Override
    public void execute(ShardingContext content) {
//        System.out.println("JobName:"+content.getJobName());
//        System.out.println("JobParameter:"+content.getJobParameter());
//        System.out.println("ShardingItem:"+content.getShardingItem());
//        System.out.println("ShardingParameter:"+content.getShardingParameter());
//        System.out.println("ShardingTotalCount:"+content.getShardingTotalCount());
//        System.out.println("TaskId:"+content.getTaskId());
//        System.out.println("---------------------------------------");
        LOG.info("付款超时时间（分钟） = {}", redisUtil.getGlobalVariableValueByCode("pay_expired"));
    }
}
