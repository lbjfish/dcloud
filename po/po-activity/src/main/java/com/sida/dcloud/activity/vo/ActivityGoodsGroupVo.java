package com.sida.dcloud.activity.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class ActivityGoodsGroupVo extends UserCentricDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("活动id")
    private String activityId;

    @ApiModelProperty("组名称")
    private String name;

    @ApiModelProperty("原价")
    private Double originalPrice;

    @ApiModelProperty("销售价")
    private Double payPrice;

    @ApiModelProperty("折扣")
    private Double discount;

    @ApiModelProperty("减免金额")
    private Double minusAmount;

    @ApiModelProperty("总量")
    private Integer quantity;

    @ApiModelProperty("余量")
    private Integer remaining;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("活动商品")
    private List<ActivityGoodsVo> activityGoodsVoList;

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

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public List<ActivityGoodsVo> getActivityGoodsVoList() {
        return activityGoodsVoList;
    }

    public void setActivityGoodsVoList(List<ActivityGoodsVo> activityGoodsVoList) {
        this.activityGoodsVoList = activityGoodsVoList;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getMinusAmount() {
        return minusAmount;
    }

    public void setMinusAmount(Double minusAmount) {
        this.minusAmount = minusAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }
}
