/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.vo;

import com.sida.xiruo.po.common.BaseEntity;
import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class ActivityOrderVo extends UserCentricDTO {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("订单编号（由规则产生）")
    private String orderNo;

    @ApiModelProperty("活动id（关联activity_info表id）")
    private String activityId;

    @ApiModelProperty("用户id（关联sys_user_activity表id）")
    private String orderUser;

    @ApiModelProperty("报名表id（关联customer_activity_signup_note表id）")
    private String noteId;

    @ApiModelProperty("订单名称")
    private String orderName;

    @ApiModelProperty("订单状态（字典activity_order_status)")
    private String activityOrderStatus;

    @ApiModelProperty("商品总金额")
    private Double goodsAmount;

    @ApiModelProperty("减免金额")
    private Double minusAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getActivityOrderStatus() {
        return activityOrderStatus;
    }

    public void setActivityOrderStatus(String activityOrderStatus) {
        this.activityOrderStatus = activityOrderStatus;
    }

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Double getMinusAmount() {
        return minusAmount;
    }

    public void setMinusAmount(Double minusAmount) {
        this.minusAmount = minusAmount;
    }
}