package com.sida.dcloud.job.util;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.sida.dcloud.job.common.JobException;
import com.sida.dcloud.job.config.ElasticJobListener;
import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.simple.SidaSimpleJob;
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

    public String createJob(JobEntity jobEntity, Consumer consumer) {
        Optional.ofNullable(jobEntity).orElseThrow(() -> new JobException("任务实体不能空"));
        Optional.ofNullable(consumer).orElseThrow(() -> new JobException("consumer不能空"));
        Optional.ofNullable(jobEntity.getId()).orElseThrow(() -> new JobException("id不能空"));
        Optional.ofNullable(jobEntity.getJobCron()).orElseThrow(() -> new JobException("cron不能空"));
        Optional.ofNullable(jobEntity.getShardingTotalCount()).orElseThrow(() -> new JobException("shardingTotalCount不能空"));
        String jobName = Optional.ofNullable(jobEntity.getJobName()).orElse(SidaSimpleJob.class.getCanonicalName());
        JobCoreConfiguration jobCoreConfiguration =
                JobCoreConfiguration.newBuilder(jobName, jobEntity.getJobCron(), jobEntity.getShardingTotalCount()).jobParameter(jobEntity.getId()).shardingItemParameters(jobEntity.getShardingItemParameters()).build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, SidaSimpleJob.class.getCanonicalName());
        LiteJobConfiguration jobConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration).build();
        JobScheduler jobScheduler = new SpringJobScheduler(new SidaSimpleJob(consumer), registryCenter, jobConfig, jobEventConfiguration, elasticJobListener);
        jobScheduler.init();
        schedulerMap.put(jobName, jobScheduler);
        return jobName;
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

    public String dropJob(String jobName) {
        LOG.info("Drop job scheduler, jobName = {}", jobName);
        Optional.ofNullable(schedulerMap.get(jobName)).ifPresent(jobScheduler -> jobScheduler.getSchedulerFacade().shutdownInstance());
        schedulerMap.remove(jobName);
        return jobName;
    }
}
