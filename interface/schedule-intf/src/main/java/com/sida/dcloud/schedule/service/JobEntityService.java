package com.sida.dcloud.schedule.service;



import com.sida.dcloud.schedule.po.JobEntity;
import java.util.List;

public interface JobEntityService {

    void updateJob(JobEntity jobEntity);

    /**
     * 通过任务ID获取任务
     * @param jobId
     * @return
     */
    JobEntity getJobById(Long jobId);

    /**
     * 查询所有可用的定时任务
     * @return
     */
    List<JobEntity> getActiveJob();

    /**
     * 重新加载定时任务
     */
    void reloadSchedule();
}