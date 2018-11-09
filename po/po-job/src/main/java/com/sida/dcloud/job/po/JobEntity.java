package com.sida.dcloud.job.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class JobEntity implements Serializable {
    private static final long serialVersionUID = 1;

    private String id;
    private String jobName;
    private String jobCron;
    private String className;
    private Integer shardingTotalCount;
    private Integer  executeCount;
    private Boolean executeStatus;
    private Date createTime;
    private Date lastExecuteTime;
    private String remark;
    private String shardingItemParameters;
    private boolean isloop;

    public boolean getIsloop() {
        return isloop;
    }

    public void setIsloop(boolean isloop) {
        this.isloop = isloop;
    }

    private Map<String, String> paramMap;

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getShardingTotalCount() {
        return shardingTotalCount;
    }

    public void setShardingTotalCount(Integer shardingTotalCount) {
        this.shardingTotalCount = shardingTotalCount;
    }

    public Integer getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(Integer executeCount) {
        this.executeCount = executeCount;
    }

    public Boolean getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(Boolean executeStatus) {
        this.executeStatus = executeStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShardingItemParameters() {
        return shardingItemParameters;
    }

    public void setShardingItemParameters(String shardingItemParameters) {
        this.shardingItemParameters = shardingItemParameters;
    }
}
