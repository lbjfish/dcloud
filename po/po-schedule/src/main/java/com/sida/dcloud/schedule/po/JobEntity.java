/**
 * create by chenguanshou
 * @date 2017-09
 */
package com.sida.dcloud.schedule.po;



import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class JobEntity implements Serializable {
    @ApiModelProperty("任务ID")
    private Long jobId;
    @ApiModelProperty("任务名称")
    private String jobName;
    @ApiModelProperty("任务SpringBeanId")
    private String jobObject;
    @ApiModelProperty("任务执行类")
    private String jobClass;
    @ApiModelProperty("任务执行方法")
    private String jobMethod;
    @ApiModelProperty("任务状态（0已停止，1运行中）")
    private Integer jobStatus;
    @ApiModelProperty("任务类型（1、周期性任务，2、一次性任务）")
    private Integer jobType;
    @ApiModelProperty("任务时间表达式")
    private String cronExpr;
    @ApiModelProperty("运行次数")
    private Long runTimes;
    @ApiModelProperty("运行持续时间")
    private Long runDuration;
    @ApiModelProperty("最后一次运行时间")
    private Date runtimeLast;
    @ApiModelProperty("下一次运行时间")
    private Date runtimeNext;
    @ApiModelProperty("任务开始时间")
    private Date syncBeginTime;
    @ApiModelProperty("任务结束时间")
    private Date syncEndTime;
    @ApiModelProperty("创建日期")
    private Date createdDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("最后修改时间")
    private Date lastUpdatedDate;
    @ApiModelProperty("最后修改人")
    private Long lastUpdatedBy;
    @ApiModelProperty("软删除标识")
    private Integer removeFlag;
    @ApiModelProperty("排序列")
    private String orderField;

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    private static final long serialVersionUID = 1L;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobObject() {
        return jobObject;
    }

    public void setJobObject(String jobObject) {
        this.jobObject = jobObject == null ? null : jobObject.trim();
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass == null ? null : jobClass.trim();
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public void setJobMethod(String jobMethod) {
        this.jobMethod = jobMethod == null ? null : jobMethod.trim();
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr == null ? null : cronExpr.trim();
    }

    public Long getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(Long runTimes) {
        this.runTimes = runTimes;
    }

    public Long getRunDuration() {
        return runDuration;
    }

    public void setRunDuration(Long runDuration) {
        this.runDuration = runDuration;
    }

    public Date getRuntimeLast() {
        return runtimeLast;
    }

    public void setRuntimeLast(Date runtimeLast) {
        this.runtimeLast = runtimeLast;
    }

    public Date getRuntimeNext() {
        return runtimeNext;
    }

    public void setRuntimeNext(Date runtimeNext) {
        this.runtimeNext = runtimeNext;
    }

    public Date getSyncBeginTime() {
        return syncBeginTime;
    }

    public void setSyncBeginTime(Date syncBeginTime) {
        this.syncBeginTime = syncBeginTime;
    }

    public Date getSyncEndTime() {
        return syncEndTime;
    }

    public void setSyncEndTime(Date syncEndTime) {
        this.syncEndTime = syncEndTime;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }
}