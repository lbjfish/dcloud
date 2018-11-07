package com.sida.dcloud.job.elastic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class SidaSimpleJob implements SimpleJob {
    private final static Logger LOG = LoggerFactory.getLogger(SidaSimpleJob.class);

    private Consumer consumer;
    public SidaSimpleJob(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void execute(ShardingContext shardingContext) {
        //分片处理
        int shardIndx = shardingContext.getShardingItem();
        switch(shardIndx) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
//        executableJob.execute();
        consumer.accept(shardingContext);
    }
}
