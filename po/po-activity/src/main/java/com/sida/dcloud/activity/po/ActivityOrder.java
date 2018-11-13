/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sida.dcloud.activity.dto.IdAndCountDto;
import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ActivityOrder extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty("支付方式（字典payment_mode）")
    private String paymentMode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("付款时间")
    private Date payTime;

    @ApiModelProperty("成交时间")
    private Date turnoverTime;

    @ApiModelProperty("评价时间")
    private Date valuateTime;

    @ApiModelProperty("关闭时间")
    private Date closeTime;

    @ApiModelProperty("退款时间")
    private Date refundTime;

    @ApiModelProperty("备注")
    private String remark;

//    @JsonIgnore
    @ApiModelProperty("订单商品列表")
    private List<IdAndCountDto> goodsList;
    @ApiModelProperty("订单商品组合列表")
    private List<IdAndCountDto> groupList;

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Date getValuateTime() {
        return valuateTime;
    }

    public void setValuateTime(Date valuateTime) {
        this.valuateTime = valuateTime;
    }

    public List<IdAndCountDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<IdAndCountDto> goodsList) {
        this.goodsList = goodsList;
    }

    public List<IdAndCountDto> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<IdAndCountDto> groupList) {
        this.groupList = groupList;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser == null ? null : orderUser.trim();
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId == null ? null : noteId.trim();
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName == null ? null : orderName.trim();
    }

    public String getActivityOrderStatus() {
        return activityOrderStatus;
    }

    public void setActivityOrderStatus(String activityOrderStatus) {
        this.activityOrderStatus = activityOrderStatus == null ? null : activityOrderStatus.trim();
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

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode == null ? null : paymentMode.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}