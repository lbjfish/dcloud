/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sida.xiruo.po.common.BaseEntity;
import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class CustomerActivitySignupNoteVo extends UserCentricDTO {
    @ApiModelProperty("id")
    private String id;
    @JsonIgnore
    private String userId;
    @JsonIgnore
    private String orderString;
    @JsonIgnore
    private String orderField;

    @ApiModelProperty("报名表编号（由规则产生）")
    private String noteNo;

    @ApiModelProperty("活动id")
    private String activityId;

    @ApiModelProperty("活动主题")
    private String title;

    @ApiModelProperty("海报url")
    private String bannerUrl;

    @ApiModelProperty("地址id")
    private String regionId;

    @ApiModelProperty("活动起始时间")
    private Date startTime;

    @ApiModelProperty("活动结束时间")
    private Date endTime;

    @ApiModelProperty("报名开始时间")
    private Date signStartTime;

    @ApiModelProperty("报名结束时间")
    private Date signEndTime;

    @ApiModelProperty("报名时间")
    private Date signupTime;

    @ApiModelProperty("订单创建时间")
    private Date createTime;

    @ApiModelProperty("付款时间")
    private Date payTime;

    @ApiModelProperty("成交时间")
    private Date turnoverTime;

    @ApiModelProperty("付款期限（分钟）")
    private Integer payExpired;

    @ApiModelProperty("商品总金额")
    private Double goodsAmount;

    @ApiModelProperty("订单状态，用于查询")
    private String orderStatus;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getTurnoverTime() {
        return turnoverTime;
    }

    public void setTurnoverTime(Date turnoverTime) {
        this.turnoverTime = turnoverTime;
    }

    public Integer getPayExpired() {
        return payExpired;
    }

    public void setPayExpired(Integer payExpired) {
        this.payExpired = payExpired;
    }

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteNo() {
        return noteNo;
    }

    public void setNoteNo(String noteNo) {
        this.noteNo = noteNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
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

    public Date getSignupTime() {
        return signupTime;
    }

    public void setSignupTime(Date signupTime) {
        this.signupTime = signupTime;
    }

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }
}