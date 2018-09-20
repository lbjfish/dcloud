package com.sida.dcloud.schedule.quartz;

/**
 * Created by chenguanshou
 * 2017/9/15.
 */

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *执行具体类中的方法
 * Quartz集群，同一时间触发器只会被一个应用所执行，当一个应用当掉，另一个应用会接管执行。
 **/

public class BeanJob extends AbstractEdiJob
{
    private static Logger logger= LoggerFactory.getLogger(BeanJob.class);
    @Override
    public void exeucuteInternal(JobExecutionContext context)
    {
        Object obj = SpringContextUtil.getBean(jobEntity.getJobObject(),context);
        try {
            Method method=obj.getClass().getMethod(jobEntity.getJobMethod());
            method.invoke(obj);
        } catch (SecurityException e) {
            logger.error("error",e);
        } catch (NoSuchMethodException e) {
            logger.error("error",e);
        } catch (IllegalArgumentException e) {
            logger.error("error",e);
        } catch (IllegalAccessException e) {
            logger.error("error",e);
        } catch (InvocationTargetException e) {
            logger.error("error",e);
        }
    }

}