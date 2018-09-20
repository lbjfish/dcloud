package com.sida.dcloud.schedule.quartz;


import com.sida.dcloud.schedule.po.JobEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chenguanshou
 * 2017/9/15.
 */
public abstract class AbstractEdiJob implements Job
{

    protected JobEntity jobEntity;
    protected static final Logger logger= LoggerFactory.getLogger(AbstractEdiJob.class);
    private Long beginTime;
    ApplicationContext applicationContext=null;


    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        preExcute();
        exeucuteInternal(context);
        Object  obj = SpringContextUtil.getBean("schedulerHelper",context);
        Method[] objArray = obj.getClass().getMethods();
        for(Method method:objArray) {
            if ("postExcutes".equals(method.getName())) {
                try {
                    JobExecutionContext contextJob=context;
                    Object object = method.invoke(obj, new Object[]{jobEntity.getJobId(), beginTime, contextJob.getFireTime(),contextJob.getNextFireTime()});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    abstract public void exeucuteInternal(JobExecutionContext context);

    public void preExcute()
    {
        beginTime=System.currentTimeMillis();
    }
    public void setJobEntity(JobEntity jobEntity) {
        this.jobEntity = jobEntity;
    }


}
