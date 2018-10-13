package com.sida.dcloud.activity.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class ActivityInfoVo extends UserCentricDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("主活动id（子活动有效，主活动为0）")
    private String parentId;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty(value = "活动标题", example = "大展")
    private String title;

    @ApiModelProperty("活动摘要")
    private String summary;

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

    @ApiModelProperty("活动报名开始时间")
    private Date signStartTime;

    @ApiModelProperty("活动报名结束时间")
    private Date signEndTime;

    @ApiModelProperty("海报图片（七牛云）")
    private String bannerUrl;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("管理部门（关联system.sys_org表id）")
    private String responsibleOrg;

    @ApiModelProperty("总费用（元）")
    private Double expenses;

    @ApiModelProperty("活动状态（字典activity_status）")
    private String activityStatus;

    @ApiModelProperty("子活动集合")
    private List<ChildActivityInfoVo> children;

    public List<ChildActivityInfoVo> getChildren() {
        return children;
    }

    public void setChildren(List<ChildActivityInfoVo> children) {
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(Date signStartTime) {
        this.signStartTime = signStartTime;
    }

    public Date getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(Date signEndTime) {
        this.signEndTime = signEndTime;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

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
