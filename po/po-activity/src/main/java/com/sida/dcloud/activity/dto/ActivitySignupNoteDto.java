package com.sida.dcloud.activity.dto;

import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitySignupNoteDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("活动id")
    private String activityId;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("选择的企业ids")
    private String companyIds;
    @ApiModelProperty("人脸采集（七牛云图片地址）")
    private String faceUrl;
    @ApiModelProperty("报名表设置版本")
    private String settingVersion;
    @ApiModelProperty("报名表")
    private CustomerActivitySignupNote customerActivitySignupNote;
    @ApiModelProperty("订单")
    private ActivityOrder activityOrder;

    public String getSettingVersion() {
        return settingVersion;
    }

    public String getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(String companyIds) {
        this.companyIds = companyIds;
    }

    public void setSettingVersion(String settingVersion) {
        this.settingVersion = settingVersion;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CustomerActivitySignupNote getCustomerActivitySignupNote() {
        return customerActivitySignupNote;
    }

    public void setCustomerActivitySignupNote(CustomerActivitySignupNote customerActivitySignupNote) {
        this.customerActivitySignupNote = customerActivitySignupNote;
    }

    public ActivityOrder getActivityOrder() {
        return activityOrder;
    }

    public void setActivityOrder(ActivityOrder activityOrder) {
        this.activityOrder = activityOrder;
    }
}
