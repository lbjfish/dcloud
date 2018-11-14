/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class ConsultationInfo extends BaseEntity implements Serializable {
    @ApiModelProperty("通用字段：禁用标识（0.可用  1.禁用）")
    private Boolean disable;

    @ApiModelProperty("报名表id（关联customer_activity_signup_note表id字段）")
    private String noteId;

    @ApiModelProperty("企业id（关联operation.company_info的id）")
    private String companyId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("用户id（关联operation.sys_user_activity的id）")
    private String userId;

    @ApiModelProperty("对接开始时间")
    private Date conStartTime;

    @ApiModelProperty("对接结束时间")
    private Date conEndTime;

    @ApiModelProperty("对接地点")
    private String conLocation;

    @ApiModelProperty("对接方式")
    private String conMode;

    @ApiModelProperty("对接安排")
    private String conScheduler;

    @ApiModelProperty("对接结论")
    private String conResult;

    @ApiModelProperty("对接效果")
    private Integer conEffect;

    @ApiModelProperty("是否成功（0：否，1：是）")
    private Boolean succeed;

    @ApiModelProperty("备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId == null ? null : noteId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getConStartTime() {
        return conStartTime;
    }

    public void setConStartTime(Date conStartTime) {
        this.conStartTime = conStartTime;
    }

    public Date getConEndTime() {
        return conEndTime;
    }

    public void setConEndTime(Date conEndTime) {
        this.conEndTime = conEndTime;
    }

    public String getConLocation() {
        return conLocation;
    }

    public void setConLocation(String conLocation) {
        this.conLocation = conLocation == null ? null : conLocation.trim();
    }

    public String getConMode() {
        return conMode;
    }

    public void setConMode(String conMode) {
        this.conMode = conMode == null ? null : conMode.trim();
    }

    public String getConScheduler() {
        return conScheduler;
    }

    public void setConScheduler(String conScheduler) {
        this.conScheduler = conScheduler == null ? null : conScheduler.trim();
    }

    public String getConResult() {
        return conResult;
    }

    public void setConResult(String conResult) {
        this.conResult = conResult == null ? null : conResult.trim();
    }

    public Integer getConEffect() {
        return conEffect;
    }

    public void setConEffect(Integer conEffect) {
        this.conEffect = conEffect;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}