/**
 * create by jianglingfeng
 * @date 2018-10
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class ActivityGoods extends BaseEntity implements Serializable {
    @ApiModelProperty("活动id（关联activity_info表id）")
    private String activityId;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品原价")
    private Double originalPrice;

    @ApiModelProperty("商品销售价")
    private Double payPrice;

    @ApiModelProperty("商品总量")
    private Integer quantity;

    @ApiModelProperty("商品余量")
    private Integer remaining;

    @ApiModelProperty("排序值（值越小越靠前）")
    private Integer sort;

    @ApiModelProperty("添加时间")
    private Date addTime;

    @ApiModelProperty("有效起始时间")
    private Date startTime;

    @ApiModelProperty("有效结束时间")
    private Date endTime;

    @ApiModelProperty("是否上架（0.否 1.是）")
    private Boolean visible;

    @ApiModelProperty("备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}