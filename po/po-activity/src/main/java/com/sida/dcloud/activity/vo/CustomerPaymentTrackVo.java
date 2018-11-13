/**
 * create by jianglingfeng
 * @date 2018-10
 */
package com.sida.dcloud.activity.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class CustomerPaymentTrackVo extends UserCentricDTO implements Serializable {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("支付金额")
    private Double payAmount;

    @ApiModelProperty("支付方式（字典payment_mode）")
    private String paymentMode;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("支付成功时间")
    private Date successTime;

    @ApiModelProperty("是否成功（0：否，1：是）")
    private Boolean succeed;

    @ApiModelProperty("商品描述")
    private String body;

    @ApiModelProperty("订单号")
    private String outTradeNo;

    private static final long serialVersionUID = 1L;

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        this.paymentMode = paymentMode;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}