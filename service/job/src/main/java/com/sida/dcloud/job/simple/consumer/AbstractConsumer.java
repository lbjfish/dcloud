package com.sida.dcloud.job.simple.consumer;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.util.JobUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

public abstract class AbstractConsumer implements Consumer<ShardingContext> {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private JobUtil jobUtil;

    public String initJob(JobEntity jobEntity) {
        try {
            jobEntity.setShardingItemParameters("0=A,1=B,2=C");
            LOG.info("initJob = {}", BeanUtils.describe(jobEntity));
        } catch(Exception e) {
            e.printStackTrace();
            throw new JobException(e);
        }
        return jobUtil.createJob(jobEntity, this);
    }

    public String releaseJob(String jobName) {
        LOG.info("releaseJob = {}", jobName);
        return jobUtil.dropJob(jobName);
    }


    @Override
    public Consumer<ShardingContext> andThen(Consumer<? super ShardingContext> after) {
        return null;
    }
}
