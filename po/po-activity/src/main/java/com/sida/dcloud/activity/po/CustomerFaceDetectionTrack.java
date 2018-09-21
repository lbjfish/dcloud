/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class CustomerFaceDetectionTrack extends BaseEntity implements Serializable {
    @ApiModelProperty("活动id（关联activity_info表id）")
    private String activityId;

    @ApiModelProperty("用户id（关联sys_user_activity表id）")
    private String userId;

    @ApiModelProperty("业务源（字典business_source）")
    private String businessSource;

    @ApiModelProperty("比对相片url（七牛云）")
    private String photoUrl;

    @ApiModelProperty("识别时间")
    private Date thatTime;

    @ApiModelProperty("开始识别时间")
    private Date thatTimeStart;

    @ApiModelProperty("结束识别时间")
    private Date thatTimeEnd;

    @ApiModelProperty("是否成功（0：否，1：是）")
    private Boolean succeed;

    @ApiModelProperty("失败原因")
    private String failedReason;

    @ApiModelProperty("备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public Date getThatTimeStart() {
        return thatTimeStart;
    }

    public void setThatTimeStart(Date thatTimeStart) {
        this.thatTimeStart = thatTimeStart;
    }

    public Date getThatTimeEnd() {
        return thatTimeEnd;
    }

    public void setThatTimeEnd(Date thatTimeEnd) {
        this.thatTimeEnd = thatTimeEnd;
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

    public String getBusinessSource() {
        return businessSource;
    }

    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource == null ? null : businessSource.trim();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    public Date getThatTime() {
        return thatTime;
    }

    public void setThatTime(Date thatTime) {
        this.thatTime = thatTime;
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