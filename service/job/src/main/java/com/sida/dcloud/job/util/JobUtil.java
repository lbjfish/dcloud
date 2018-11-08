package com.sida.dcloud.job.util;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.elastic.ConFunJob;
import com.sida.dcloud.job.elastic.SidaDataflowJob;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.elastic.SidaSimpleJob;
import com.sida.xiruo.common.util.Xiruo;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public final class JobUtil {
    private final static Logger LOG = LoggerFactory.getLogger(JobUtil.class);
    private static final String JOB_NAME_TEMPLATE = "%s:%s";
    private static final String JOB_NAME_DEFAULT = "JOB";

    private static JobUtil jobUtil;

    public static JobUtil getJobUtil() {
        return jobUtil;
    }

//    private Map<String, JobScheduler> schedulerMap = new HashMap<>();

    @Resource
    private ZookeeperRegistryCenter registryCenter;
    @Resource
    private JobEventConfiguration jobEventConfiguration;
    @Resource
    private ElasticJobListener elasticJobListener;
    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

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
        json.put("loop", jobEntity.isLoop());
//        jobName = Optional.ofNullable(jobEntity.getJobName()).orElse(JOB_NAME_DEFAULT);
        jobName = String.format(JOB_NAME_TEMPLATE, JOB_NAME_DEFAULT, jobEntity.getId());
        if(obj instanceof Consumer) {
            job = new SidaSimpleJob((Consumer)obj);
            String desc = String.format(JOB_NAME_TEMPLATE, Optional.ofNullable(jobEntity.getJobName()).orElse(SidaSimpleJob.class.getCanonicalName()), Xiruo.getNow());
            JobCoreConfiguration jobCoreConfiguration =
                    JobCoreConfiguration.newBuilder(jobName, jobEntity.getJobCron(), jobEntity.getShardingTotalCount()).description(desc).jobParameter(json.toJSONString()).shardingItemParameters(jobEntity.getShardingItemParameters()).build();
            jobTypeConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, jobName);
        } else if (obj instanceof ConFunJob) {
            job = new SidaDataflowJob((ConFunJob) obj);
            String desc = String.format(JOB_NAME_TEMPLATE, Optional.ofNullable(jobEntity.getJobName()).orElse(SidaDataflowJob.class.getCanonicalName()), Xiruo.getNow());
            JobCoreConfiguration jobCoreConfiguration =
                    JobCoreConfiguration.newBuilder(jobName, jobEntity.getJobCron(), jobEntity.getShardingTotalCount()).description(desc).jobParameter(json.toJSONString()).shardingItemParameters(jobEntity.getShardingItemParameters()).build();
            jobTypeConfiguration = new DataflowJobConfiguration(jobCoreConfiguration, jobName, false);
        } else {
            throw new JobException("传入的obj类型不被支持");
        }

        LiteJobConfiguration jobConfig = LiteJobConfiguration.newBuilder(jobTypeConfiguration).build();
        JobScheduler jobScheduler = new SpringJobScheduler(job, registryCenter, jobConfig, jobEventConfiguration, elasticJobListener);
        LOG.info("{} - Cron字符串: {}", jobEntity.getJobName(), jobEntity.getJobCron());
        jobScheduler.init();
//        schedulerMap.put(jobEntity.getId(), jobScheduler);
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
        return  dropJob(jobId, JOB_NAME_DEFAULT);
    }

    public String dropJob(String jobId, String jobName) {
        jobName = String.format(JOB_NAME_TEMPLATE, jobName, jobId);
        LOG.info("Drop job scheduler, jobId = {}", jobName);
//        Optional.ofNullable(schedulerMap.remove(jobId)).ifPresent(jobScheduler -> jobScheduler.getSchedulerFacade().shutdownInstance());
        CuratorFramework client = zookeeperRegistryCenter.getClient();
        try {
            if(exists(jobId)) {
                client.delete().deletingChildrenIfNeeded().forPath("/" + jobName);
                LOG.info("删除任务: {}", jobName);
            } else {
                LOG.warn("任务不存在: {}", jobName);
            }
        } catch(Exception e) {
            LOG.error("删除任务失败: ", e);
            throw new JobException(e);
        }
        return jobName;
    }

    public boolean exists(String jobId) {
        return exists(jobId, JOB_NAME_DEFAULT);
    }

    public boolean exists(String jobId, String jobName) {
//        return Optional.ofNullable(schedulerMap.get(jobId)).isPresent();
        jobName = String.format(JOB_NAME_TEMPLATE, jobName, jobId);
        CuratorFramework client = zookeeperRegistryCenter.getClient();
        try {
            return client.checkExists().forPath("/" + jobName) != null;
        } catch(Exception e) {
            LOG.error("查询任务失败: ", e);
            throw new JobException(e);
        }
    }
}
