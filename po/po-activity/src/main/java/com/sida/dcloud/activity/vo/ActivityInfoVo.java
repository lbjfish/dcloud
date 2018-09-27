package com.sida.dcloud.activity.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ActivityInfoVo extends UserCentricDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("主办单位")
    private String hosts;

    @ApiModelProperty("是否多活动（0：否，1：是）")
    private Boolean multiActivity;

    @ApiModelProperty("是否分段（0：否，1：是）")
    private Boolean multiSection;

    @ApiModelProperty("地点id（关联system.sys_region表id）")
    private String regionId;

    @ApiModelProperty("活动起始时间")
    private Date startTime;

    @ApiModelProperty("活动结束时间")
    private Date endTime;

    @ApiModelProperty("管理部门（关联system.sys_org表id）")
    private String responsibleOrg;

    @ApiModelProperty("总费用（元）")
    private Double expenses;

    @ApiModelProperty("活动状态（字典activity_status）")
    private String activityStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public Boolean getMultiActivity() {
        return multiActivity;
    }

    public void setMultiActivity(Boolean multiActivity) {
        this.multiActivity = multiActivity;
    }

    public Boolean getMultiSection() {
        return multiSection;
    }

    public void setMultiSection(Boolean multiSection) {
        this.multiSection = multiSection;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getResponsibleOrg() {
        return responsibleOrg;
    }

    public void setResponsibleOrg(String responsibleOrg) {
        this.responsibleOrg = responsibleOrg;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }
}
