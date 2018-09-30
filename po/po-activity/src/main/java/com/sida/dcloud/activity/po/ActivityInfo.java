/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ActivityInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("活动编码")
    private String code;

    @ApiModelProperty("活动别名")
    private String alias;

    @ApiModelProperty("活动标题")
    private String title;

    @ApiModelProperty("活动副标题")
    private String secondTitle;

    @ApiModelProperty("活动描述")
    private String content;

    @ApiModelProperty("自定义标题")
    private String customTitle;

    @ApiModelProperty("自定义内容")
    private String customContent;

    @ApiModelProperty("主办单位")
    private String hosts;

    @ApiModelProperty("承办单位")
    private String organizers;

    @ApiModelProperty("协办单位")
    private String coOrganizers;

    @ApiModelProperty("联系人")
    private String linkMan;

    @ApiModelProperty("联系电话")
    private String linkPhone;

    @ApiModelProperty("是否多活动（0：否，1：是）")
    private Boolean multiActivity;

    @ApiModelProperty("是否分段（0：否，1：是）")
    private Boolean multiSection;

    @ApiModelProperty("主活动id（若是单体活动则为0）")
    private String parentId;

    @ApiModelProperty("地点id（关联system.sys_region表id）")
    private String regionId;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("海报图片（七牛云）")
    private String bannerUrl;

    @ApiModelProperty("活动起始时间")
    private Date startTime;

    @ApiModelProperty("活动结束时间")
    private Date endTime;

    @ApiModelProperty("报名开始时间")
    private Date signStartTime;

    @ApiModelProperty("报名结束时间")
    private Date signEndTime;

    @ApiModelProperty("审核通过时间")
    private Date approvalTime;

    @ApiModelProperty("管理部门（关联system.sys_org表id）")
    private String responsibleOrg;

    @ApiModelProperty("总费用（元）")
    private Double expenses;

    @ApiModelProperty("收藏数")
    private Integer favoriteCount;

    @ApiModelProperty("报名数")
    private Integer signCount;

    @ApiModelProperty("评论数")
    private Integer commentCount;

    @ApiModelProperty("报名是否独立入口（0：否，1：是）")
    private Boolean aloneEntrance;

    @ApiModelProperty("报名独立入口url")
    private String entranceUrl;

    @ApiModelProperty("活动状态（字典activity_status）")
    private String activityStatus;

    @ApiModelProperty("启用人脸识别（0：否，1：是）")
    private Boolean faceDetection;

    @ApiModelProperty("门票单人购买数量限制")
    private Integer buyLimit;

    @ApiModelProperty("门票备注")
    private String ticketRemark;

    @ApiModelProperty("付款时效（分钟）")
    private Integer payExpired;

    @ApiModelProperty("付款说明")
    private String payRemark;

    @ApiModelProperty("退款说明")
    private String refundRemark;

    @ApiModelProperty("是否支持退款（0：否，1：是）")
    private Boolean allowRefund;

    @ApiModelProperty("备注")
    private String remark;

    @JsonIgnore
    @ApiModelProperty("商品集合")
    private List<ActivityGoods> activityGoodsList;

    public List<ActivityGoods> getActivityGoodsList() {
        return activityGoodsList;
    }

    public void setActivityGoodsList(List<ActivityGoods> activityGoodsList) {
        this.activityGoodsList = activityGoodsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle == null ? null : secondTitle.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle == null ? null : customTitle.trim();
    }

    public String getCustomContent() {
        return customContent;
    }

    public void setCustomContent(String customContent) {
        this.customContent = customContent == null ? null : customContent.trim();
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts == null ? null : hosts.trim();
    }

    public String getOrganizers() {
        return organizers;
    }

    public void setOrganizers(String organizers) {
        this.organizers = organizers == null ? null : organizers.trim();
    }

    public String getCoOrganizers() {
        return coOrganizers;
    }

    public void setCoOrganizers(String coOrganizers) {
        this.coOrganizers = coOrganizers == null ? null : coOrganizers.trim();
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone == null ? null : linkPhone.trim();
    }

    public Boolean getMultiActivity() {
        return multiActivity;
    }

    public void setMultiActivity(Boolean multiActivity) {
        this.multiActivity = multiActivity;
    }

    public Boolean getMultiSection() {
        return multiSection;
    }

    public void setMultiSection(Boolean multiSection) {
        this.multiSection = multiSection;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl == null ? null : bannerUrl.trim();
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

    public Date getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(Date signStartTime) {
        this.signStartTime = signStartTime;
    }

    public Date getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(Date signEndTime) {
        this.signEndTime = signEndTime;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getResponsibleOrg() {
        return responsibleOrg;
    }

    public void setResponsibleOrg(String responsibleOrg) {
        this.responsibleOrg = responsibleOrg == null ? null : responsibleOrg.trim();
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Boolean getAloneEntrance() {
        return aloneEntrance;
    }

    public void setAloneEntrance(Boolean aloneEntrance) {
        this.aloneEntrance = aloneEntrance;
    }

    public String getEntranceUrl() {
        return entranceUrl;
    }

    public void setEntranceUrl(String entranceUrl) {
        this.entranceUrl = entranceUrl == null ? null : entranceUrl.trim();
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus == null ? null : activityStatus.trim();
    }

    public Boolean getFaceDetection() {
        return faceDetection;
    }

    public void setFaceDetection(Boolean faceDetection) {
        this.faceDetection = faceDetection;
    }

    public Integer getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(Integer buyLimit) {
        this.buyLimit = buyLimit;
    }

    public String getTicketRemark() {
        return ticketRemark;
    }

    public void setTicketRemark(String ticketRemark) {
        this.ticketRemark = ticketRemark == null ? null : ticketRemark.trim();
    }

    public Integer getPayExpired() {
        return payExpired;
    }

    public void setPayExpired(Integer payExpired) {
        this.payExpired = payExpired;
    }

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark == null ? null : payRemark.trim();
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark == null ? null : refundRemark.trim();
    }

    public Boolean getAllowRefund() {
        return allowRefund;
    }

    public void setAllowRefund(Boolean allowRefund) {
        this.allowRefund = allowRefund;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}