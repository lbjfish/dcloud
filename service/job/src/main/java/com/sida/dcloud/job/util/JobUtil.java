package com.sida.dcloud.job.util;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.config.ElasticJobListener;
import com.sida.dcloud.job.elastic.ConFunJob;
import com.sida.dcloud.job.elastic.SidaDataflowJob;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.elastic.SidaSimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public final class JobUtil {
    private final static Logger LOG = LoggerFactory.getLogger(JobUtil.class);
    private static JobUtil jobUtil;

    public static JobUtil getJobUtil() {
        return jobUtil;
    }

    private Map<String, JobScheduler> schedulerMap = new HashMap<>();

    @Resource
    private ZookeeperRegistryCenter registryCenter;
    @Resource
    private JobEventConfiguration jobEventConfiguration;
    @Resource
    private ElasticJobListener elasticJobListener;

    @PostConstruct
    private void init() {
        JobUtil.jobUtil = this;
    }

    public String createJob(JobEntity jobEntity, Object obj) {
        Optional.ofNullable(jobEntity).orElseThrow(() -> new JobException("任务实体不能空"));
        Optional.ofNullable(obj).orElseThrow(() -> new JobException("obj不能空"));
        Optional.ofNullable(jobEntity.getId()).orElseThrow(() -> new JobException("id不能空"));
        Optional.ofNullable(jobEntity.getJobCron()).orElseThrow(() -> new JobException("cron不能空"));
        Optional.ofNullable(jobEntity.getShardingTotalCount()).orElseThrow(() -> new JobException("shardingTotalCount不能空"));
        ElasticJob job;
        String jobName;
        JobTypeConfiguration jobTypeConfiguration;
        JSONObject json = new JSONObject();
        Optional.ofNullable(jobEntity.getParamMap()).ifPresent(json::putAll);
        json.put("jobId", jobEntity.getId());
        if(obj instanceof Consumer) {
            job = new SidaSimpleJob((Consumer)obj);
            jobName = Optional.ofNullable(jobEntity.getJobName()).orElse(SidaSimpleJob.class.getCanonicalName());
            JobCoreConfiguration jobCoreConfiguration =
                    JobCoreConfiguration.newBuilder(jobName, jobEntity.getJobCron(), jobEntity.getShardingTotalCount()).jobParameter(json.toJSONString()).shardingItemParameters(jobEntity.getShardingItemParameters()).build();
            jobTypeConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, jobName);
        } else if (obj instanceof ConFunJob) {
            job = new SidaDataflowJob((ConFunJob) obj);
            jobName = Optional.ofNullable(jobEntity.getJobName()).orElse(SidaDataflowJob.class.getCanonicalName());
            JobCoreConfiguration jobCoreConfiguration =
                    JobCoreConfiguration.newBuilder(jobName, jobEntity.getJobCron(), jobEntity.getShardingTotalCount()).jobParameter(json.toJSONString()).shardingItemParameters(jobEntity.getShardingItemParameters()).build();
            jobTypeConfiguration = new DataflowJobConfiguration(jobCoreConfiguration, jobName, false);
        } else {
            throw new JobException("传入的obj类型不被支持");
        }

        LiteJobConfiguration jobConfig = LiteJobConfiguration.newBuilder(jobTypeConfiguration).build();
        JobScheduler jobScheduler = new SpringJobScheduler(job, registryCenter, jobConfig, jobEventConfiguration, elasticJobListener);
        jobScheduler.init();
        schedulerMap.put(jobEntity.getId(), jobScheduler);
        return jobEntity.getId();
    }

//    private SimpleJob createSimpleJob(String className) {
//        try {
//            Object instance = Class.forName(className);
//            if(!(instance instanceof SimpleJob)) {
//                throw new JobException(String.format("必须使用实现 %s 的任务类", SimpleJob.class.getCanonicalName()));
//            }
//            return (SimpleJob) instance;
//        } catch(ClassNotFoundException e) {
//            throw new JobException(e);
//        }
//    }

    public String dropJob(String jobId) {
        LOG.info("Drop job scheduler, jobId = {}", jobId);
        Optional.ofNullable(schedulerMap.remove(jobId)).ifPresent(jobScheduler -> jobScheduler.getSchedulerFacade().shutdownInstance());
        return jobId;
    }

    public boolean exists(String jobId) {
        return Optional.ofNullable(schedulerMap.get(jobId)).isPresent();
    }
}
