/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class CustomerPaymentTrack extends BaseEntity implements Serializable {
    @ApiModelProperty("活动id（关联activity_info表id）")
    private String activityId;

    @ApiModelProperty("用户id（关联sys_user_activity表id）")
    private String userId;

    @ApiModelProperty("支付金额")
    private Double payAmount;

    @ApiModelProperty("支付金额下限")
    private Double payAmountDown;

    @ApiModelProperty("支付金额上限")
    private Double payAmountUp;

    @ApiModelProperty("支付方式（字典payment_mode）")
    private String paymentMode;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("支付开始时间")
    private Date payTimeStart;

    @ApiModelProperty("支付结束时间")
    private Date payTimeEnd;

    @ApiModelProperty("是否成功（0：否，1：是）")
    private Boolean succeed;

    @ApiModelProperty("失败原因")
    private String failedReason;

    @ApiModelProperty("备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public Double getPayAmountDown() {
        return payAmountDown;
    }

    public void setPayAmountDown(Double payAmountDown) {
        this.payAmountDown = payAmountDown;
    }

    public Double getPayAmountUp() {
        return payAmountUp;
    }

    public void setPayAmountUp(Double payAmountUp) {
        this.payAmountUp = payAmountUp;
    }

    public Date getPayTimeStart() {
        return payTimeStart;
    }

    public void setPayTimeStart(Date payTimeStart) {
        this.payTimeStart = payTimeStart;
    }

    public Date getPayTimeEnd() {
        return payTimeEnd;
    }

    public void setPayTimeEnd(Date payTimeEnd) {
        this.payTimeEnd = payTimeEnd;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode == null ? null : paymentMode.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}