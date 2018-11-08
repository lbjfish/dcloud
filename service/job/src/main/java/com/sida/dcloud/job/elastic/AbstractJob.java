package com.sida.dcloud.job.elastic;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.util.JobUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractJob {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    public static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    @Autowired
    private JobUtil jobUtil;

    public String initJob(JobEntity jobEntity) {
        try {
            //删除同名旧任务
            releaseJob(jobEntity.getId());
            jobEntity.setShardingItemParameters("0=A,1=B,2=C");
            LOG.info("initJob = {}", BeanUtils.describe(jobEntity));
        } catch(Exception e) {
            e.printStackTrace();
            throw new JobException(e);
        }
        return jobUtil.createJob(jobEntity, this);
    }

    public String releaseJob(String jobId) {
        return jobUtil.dropJob(jobId);
    }

    public String releaseJob(ShardingContext shardingContext) {
        JSONObject json = (JSONObject)JSONObject.parse(shardingContext.getJobParameter());
        return releaseJob(json.getString("jobId"));
    }
}
