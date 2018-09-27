package com.sida.dcloud.activity.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

public class ActivityGoodsGroupVo extends UserCentricDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("活动id")
    private String activityId;

    @ApiModelProperty("组名称")
    private String name;

    @ApiModelProperty("折扣")
    private Double discount;

    @ApiModelProperty("减免金额")
    private Double minusAmount;

    @ApiModelProperty("总量")
    private Integer quantity;

    @ApiModelProperty("余量")
    private Integer remaining;

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
