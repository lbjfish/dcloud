package com.sida.dcloud.job.elastic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.sida.dcloud.job.po.JobEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SidaDataflowJob implements DataflowJob<Map<String, String>> {
    private final static Logger LOG = LoggerFactory.getLogger(SidaDataflowJob.class);

    private ConFunJob conFunJob;
    public SidaDataflowJob(ConFunJob conFunJob) {
        this.conFunJob = conFunJob;
    }

    @Override
    public List<Map<String, String>> fetchData(ShardingContext shardingContext) {
        return conFunJob.apply(shardingContext);
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Map<String, String>> data) {
        conFunJob.accept(shardingContext, data);
    }
}
