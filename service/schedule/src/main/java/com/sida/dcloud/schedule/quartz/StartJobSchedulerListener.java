package com.sida.dcloud.schedule.quartz;

import org.quartz.SchedulerException;
import org.quartz.listeners.SchedulerListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by chenguanshou
 * 2017/9/15.
 */
@Component(value="startJobSchedulerListener")
public class StartJobSchedulerListener extends SchedulerListenerSupport
{
    private SchedulerHelper schedulerHelper;
    Logger logger = LoggerFactory.getLogger(StartJobSchedulerListener.class);

    @Override
    public void schedulerStarted()
    {
        try {
            schedulerHelper.createActiveJobFromDB();
        } catch (SchedulerException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            logger.error("createActiveJobFromDB", e);
        }
    }

    public SchedulerHelper getSchedulerHelper() {
        return schedulerHelper;
    }

    public void setSchedulerHelper(SchedulerHelper schedulerHelper) {
        this.schedulerHelper = schedulerHelper;
    }


}
