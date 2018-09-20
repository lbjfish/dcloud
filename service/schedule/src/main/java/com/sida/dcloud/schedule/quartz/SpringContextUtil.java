package com.sida.dcloud.schedule.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by chenguanshou
 * 2017/9/15.
 */
@Component("springContexUtil")
public class SpringContextUtil {
    private static ApplicationContext applicationContext;
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
            SchedulerHelper obj = (SchedulerHelper)applicationContext.getBean("schedulerHelper");
            obj.setContex(applicationContext);
        }
    }

    //通过name获取 Bean.
    public static Object getBean(String name,JobExecutionContext jbContext){
        ApplicationContext ap = null;
        try {
            ap = (ApplicationContext)jbContext.getScheduler().getContext().get("springContex");
            return ap.getBean(name);
        } catch (SchedulerException e) {

        }
        return null;
    }




}
