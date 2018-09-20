package com.sida.dcloud.schedule.quartz;


import com.sida.dcloud.schedule.config.SyncConfig;
import com.sida.dcloud.schedule.po.JobEntity;
import com.sida.dcloud.schedule.service.JobEntityService;
import com.sida.xiruo.xframework.util.DESUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by chenguanshou
 * 2017/9/15.
 */
@Component("schedulerHelper")
public class SchedulerHelper {
    private static final String CONFIG_FILE= "quartz-job.properties";
    private static final String IDENTITY_JOB_PREFIX="job_";
    private static final String IDENTITY_TRIGGER_PREFIX="trigger_";
    private static final String USER="org.quartz.dataSource.myDS.user";
    private static final String PWD="org.quartz.dataSource.myDS.password";
    private static final String URL="org.quartz.dataSource.myDS.URL";
    private static final String DRIVERCLASSNAME="org.quartz.dataSource.myDS.driver";
    public static final String SCHEDULER_KEY_JOBSERVICE="job_service";
    private static Logger logger = LoggerFactory.getLogger(SchedulerHelper.class);



    private Scheduler scheduler;

    //jobService 这个服务是实现管理任务的页面的服务实现
    @Autowired
    private JobEntityService jobService;


    //实现自己的Scheduler监听器，程序启动时，任务没创建时就创建
    @Autowired
    private StartJobSchedulerListener startJobSchedulerListener;
    @Autowired
    private SyncConfig syncConfig;
    /**
     * 服务一启动，类实例化时就执行
     */
    @PostConstruct
    public void  init()
    {
        try{

            // 创建一个定时器工厂
            StdSchedulerFactory sf = new StdSchedulerFactory();
            //初始化quartz-job.properties配置文件
            Properties prop = new Properties();

            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE));
            String user = syncConfig.getUserName();
            String pwd =syncConfig.getPassword();
            String url = syncConfig.getUrl();
            String driverClassName = syncConfig.getDriverClassName();
            user= DESUtils.getDecryptString(user);
            pwd =DESUtils.getDecryptString(pwd);
            prop.remove(USER);
            prop.remove(PWD);
            prop.remove(URL);
            prop.remove(DRIVERCLASSNAME);
            prop.put(USER,user);
            prop.put(PWD,pwd);
            prop.put(URL,url);
            prop.put(DRIVERCLASSNAME,driverClassName);
            sf.initialize(prop);
            //sf.initialize(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE));
            scheduler = sf.getScheduler();
          //  scheduler.getContext().p
            //把jobService放到scheduler上下文，job执行是可以获取并访问。
            scheduler.getContext().put(SCHEDULER_KEY_JOBSERVICE, jobService);
            scheduler.getContext().put("scheduler", scheduler);
            startJobSchedulerListener.setSchedulerHelper(this);
            //设置自己的监听器
            scheduler.getListenerManager().addSchedulerListener(startJobSchedulerListener);
            //启动定时器
            scheduler.start();
            logger.info("====================job scheduler start");
        } catch (IOException e) {
            e.printStackTrace();
        }catch(SchedulerException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            logger.error("error",e);
        }

    }


    /**
     * 根据jobentity创建并开始任务
     */
    public boolean createAndStartJob(JobEntity job)
    {
        JobDetail jobDetail=generateJobDetail(job);
        Trigger trigger=generateTriggerBuilder(job).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("scheduler.scheduleJob",e);
            return false;
        }
    }
    /**
     * 清除
     */
    public void clearAllScheduler()
    {
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            logger.error("clearAllScheduler",e);
        }
    }


    /**
     * 根据jobId和类型删除
     */
    public boolean removeJob(Long jobId,Integer jobType)
    {
        try {
            scheduler.deleteJob(getJobKey(jobId, jobType));
            return true;
        } catch (SchedulerException e) {
            logger.error("removeJob",e);
            return false;
        }
    }

    /**
     * 暂停任务
     */
    public boolean pauseJob(Long jobId,Integer jobType)
    {
        try {
            scheduler.pauseJob(getJobKey(jobId,jobType));
            return true;
        } catch (SchedulerException e) {
            logger.error("resumeJob",e);
            return false;
        }
    }

    /**
     * 马上只执行一次任务
     */
    public boolean executeOneceJob(Long jobId,Integer jobType)
    {
        try {
            Calendar end=Calendar.getInstance();
            TriggerBuilder<SimpleTrigger> simpleTriggerBuilder= TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(jobId,jobType))
                    .forJob(getJobKey(jobId,jobType))
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2));
            end.add(Calendar.SECOND, 2);
            simpleTriggerBuilder.startAt(end.getTime());
            end.add(Calendar.SECOND, 5);
            simpleTriggerBuilder.endAt(end.getTime());
            JobEntity job=jobService.getJobById(jobId);

            JobDataMap jobDataMap=new JobDataMap();
            jobDataMap.put("jobEntity", job);
            simpleTriggerBuilder.usingJobData(jobDataMap);
            Trigger trigger=simpleTriggerBuilder.build();

            scheduler.scheduleJob(trigger);
            return true;
        } catch (SchedulerException e) {
            logger.error("executeOneceJob",e);
            return false;
        }
    }
    /**
     * 启动一些scheduler里没有的active的jobDetail
     */
    public void createActiveJobFromDB() throws SchedulerException
    {
        List<JobEntity> jobs=jobService.getActiveJob();
        for(JobEntity job:jobs)
        {
            JobDetail jobDetail = scheduler.getJobDetail(getJobKey(job));
            if(jobDetail == null && job.getJobStatus() == 1) {
                createAndStartJob(job);
            } else {
                //如果定时器发生失效，删除重启定时器
                updateAndRemoveJob(job);
            }
        }
    }
    public   void setContex(ApplicationContext context) {
        try {
            scheduler.getContext().put("springContex",context);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    public void updateAndRemoveJob(JobEntity job) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(getJobKey(job));
            if(jobDetail != null) {

                if(job.getJobStatus() == 0) {
                    //已停止的任务从调度器中删除
                    scheduler.deleteJob(getJobKey(job));
                    return;
                }
                String jobObject = jobDetail.getJobDataMap().get("jobObject").toString();
                String jobClass = jobDetail.getJobDataMap().get("jobClass").toString();
                String jobMethod = jobDetail.getJobDataMap().get("jobMethod").toString();
                String jobCron = jobDetail.getJobDataMap().get("jobCron").toString();
                Object obj = jobDetail.getJobDataMap().get("jobEntity");
                if (!jobCron.equals(job.getCronExpr())
                        || !jobObject.equals(job.getJobObject())
                        || !jobMethod.equals(job.getJobMethod())
                        ||!jobClass.equals(job.getJobClass())) {
                    //时间、bean、执行方法任何一个属性不相同，重新设置触发器
                    scheduler.deleteJob(getJobKey(job));
                    createAndStartJob(job);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得任务的jobKey
     */
    public static JobKey getJobKey(Long jobId,Integer jobType)
    {
        return new JobKey(IDENTITY_JOB_PREFIX+jobId,IDENTITY_JOB_PREFIX+jobType);
    }

    /**
     * 获得任务的jobKey
     */

    public static JobKey getJobKey(JobEntity job)
    {
        return new JobKey(IDENTITY_JOB_PREFIX+job.getJobId(),IDENTITY_JOB_PREFIX+job.getJobType());
}

    /**
     * 获得trigger的triggerkey
     */
    public static TriggerKey getTriggerKey(JobEntity job)
    {
        return new TriggerKey(IDENTITY_TRIGGER_PREFIX+job.getJobId()+"_"+System.currentTimeMillis(), IDENTITY_TRIGGER_PREFIX+job.getJobType());
    }


    /**
     * 获得trigger的triggerkey
     */
    public static TriggerKey getTriggerKey(Long jobId,Integer jobType)
    {
        return new TriggerKey(IDENTITY_TRIGGER_PREFIX+jobId+"_"+System.currentTimeMillis(), IDENTITY_TRIGGER_PREFIX+jobType);
    }

    public static JobDetail generateJobDetail(JobEntity job)
    {
        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put("jobObject", job.getJobObject());
        jobDataMap.put("jobClass", job.getJobClass());
        jobDataMap.put("jobMethod", job.getJobMethod());
        jobDataMap.put("jobCron", job.getCronExpr());
        jobDataMap.put("jobEntity",job);
        Class<? extends Job> clazz=null;
        clazz=BeanJob.class;
        return JobBuilder.newJob(clazz)
                .withIdentity(getJobKey(job))
                .usingJobData(jobDataMap)
                .requestRecovery(true).storeDurably(true)
                .build();
    }


    /**
     * 根据jobEntity获得trigger
     */

    public static TriggerBuilder<CronTrigger> generateTriggerBuilder(JobEntity job)
    {
        TriggerBuilder<CronTrigger> triggerBuilder= TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(job))
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpr())
                        .withMisfireHandlingInstructionDoNothing());
        if(job.getSyncBeginTime()!=null)
            triggerBuilder.startAt(job.getSyncBeginTime());
        else
            triggerBuilder.startNow();

        if(job.getSyncEndTime()!=null)
            triggerBuilder.endAt(job.getSyncEndTime());

        return triggerBuilder;
    }
    public void postExcutes(Long jobId,Long beginTime,Date fireTime,Date nextFireTime)
    {
        //jobEntity, beginTime, context
        //获得最新的jobEntiry
        JobEntity jobEntity=jobService.getJobById(jobId);
        if(jobEntity==null)
        {
            logger.warn(jobEntity.getJobId()+"job不存在");
            return;
        }

        if(fireTime!=null)
            jobEntity.setRuntimeLast(fireTime);
        if(nextFireTime!=null)
            jobEntity.setRuntimeNext(nextFireTime);

        Long times=jobEntity.getRunTimes();
        jobEntity.setRunTimes((times==null?0l:times)+1);
        Long duration=jobEntity.getRunDuration();
        jobEntity.setRunDuration((duration==null?0l:duration)+(System.currentTimeMillis() - beginTime));
        jobService.updateJob(jobEntity);
    }
}
