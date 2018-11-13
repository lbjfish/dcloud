package com.sida.dcloud.job.elastic;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.config.ElasticJobEachListener;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.util.JobUtil;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractJob {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractJob.class);
    public static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    @Autowired
    private JobUtil jobUtil;
    @Autowired
    protected RedisUtil redisUtil;

    protected void checkExecution(Object result, ShardingContext shardingContext) {
        boolean removable = false;
        if((result instanceof Integer)) {
            if(Xiruo.nullToInt(result) == 0) {
                removable = true;
            }
        } else {
            if(result != null) {
                removable = true;
            }
        }
        if(!removable) {
            LOG.warn("任务未执行成功: {}", shardingContext.getJobName());
        }

        redisUtil.putInMap(ElasticJobEachListener.REDIS_KEY_JOB_REMOVABLE, shardingContext.getJobName(), removable);
    }

    public String initJob(JobEntity jobEntity) {
        try {
            //删除同名旧任务
            jobUtil.dropDefaultJob(jobEntity.getId());
            if(jobEntity.isTrigger()) {
                jobUtil.dropTriggerJob(jobEntity.getId());
            }
            jobEntity.setShardingItemParameters("0=A,1=B,2=C");
            LOG.info("initJob = {}", BeanUtils.describe(jobEntity));
        } catch(Exception e) {
            e.printStackTrace();
            throw new JobException(e);
        }
        return jobUtil.createJob(jobEntity, this);
    }

    protected static JSONObject toJson(ShardingContext shardingContext) {
        return (JSONObject)JSONObject.parse(shardingContext.getJobParameter());
    }
}
