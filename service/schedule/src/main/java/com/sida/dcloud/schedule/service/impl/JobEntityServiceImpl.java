package com.sida.dcloud.schedule.service.impl;



import com.sida.dcloud.schedule.dao.JobEntityMapper;
import com.sida.dcloud.schedule.po.JobEntity;
import com.sida.dcloud.schedule.quartz.SchedulerHelper;
import com.sida.dcloud.schedule.service.JobEntityService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobEntityServiceImpl implements JobEntityService {

    @Autowired
    private JobEntityMapper JobEntityMapper;

    @Autowired
    private SchedulerHelper schedulerHelper;



    public void updateJob(JobEntity jobEntity) {
        JobEntityMapper.updateByPrimaryKeySelective(jobEntity);
    }

    public JobEntity getJobById(Long jobId) {
        return JobEntityMapper.selectByPrimaryKey(jobId);
    }

    public List<JobEntity> getActiveJob() {
        JobEntity query = new JobEntity();
        query.setRemoveFlag(0);
        List<JobEntity> jobEntities = JobEntityMapper.selectByCondition(query);
        return jobEntities;
    }

    public void reloadSchedule() {
        try {
            schedulerHelper.createActiveJobFromDB();
        } catch (SchedulerException e) {
            e.printStackTrace();
            //throw new Exception("重新加载定时任务失败"+e.getMessage());
        }
    }
}