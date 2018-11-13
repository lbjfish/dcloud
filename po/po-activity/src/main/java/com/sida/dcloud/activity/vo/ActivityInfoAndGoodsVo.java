package com.sida.dcloud.activity.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class ActivityInfoAndGoodsVo extends UserCentricDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty(value = "活动标题", example = "大展")
    private String title;

    @ApiModelProperty("地点id（关联system.sys_region表id）")
    private String regionId;

    @ApiModelProperty("活动起始时间")
    private Date startTime;

    @ApiModelProperty("海报图片（七牛云）")
    private String bannerUrl;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("总费用（元）")
    private Double expenses;

    @ApiModelProperty("活动商品")
    private List<ActivityGoodsVo> activityGoodsVoList;

    @ApiModelProperty("活动商品")
    private List<ActivityGoodsGroupVo> activityGoodsGroupVoList;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public List<ActivityGoodsVo> getActivityGoodsVoList() {
        return activityGoodsVoList;
    }

    public void setActivityGoodsVoList(List<ActivityGoodsVo> activityGoodsVoList) {
        this.activityGoodsVoList = activityGoodsVoList;
    }

    public List<ActivityGoodsGroupVo> getActivityGoodsGroupVoList() {
        return activityGoodsGroupVoList;
    }

    public void setActivityGoodsGroupVoList(List<ActivityGoodsGroupVo> activityGoodsGroupVoList) {
        this.activityGoodsGroupVoList = activityGoodsGroupVoList;
    }
}
