/**
 * create by user
 * @date 2018-11
 */
package com.sida.dcloud.content.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Information extends BaseEntity implements Serializable {
    @ApiModelProperty("通用字段：禁用标识（0.可用  1.禁用）")
    private Boolean disable;

    @ApiModelProperty("资讯标题")
    private String title;

    @ApiModelProperty("资讯类型（字典information_type）")
    private String infoType;

    @ApiModelProperty("资讯封面图片（七牛云）")
    private String bannerUrl;

    @ApiModelProperty("资讯详情图")
    private String bannerDetailUrl;

    @ApiModelProperty("视频贴片图片（七牛云）")
    private String firstUrl;

    @ApiModelProperty("视频url地址（七牛云）")
    private String vedioUrl;

    @ApiModelProperty("资讯详情")
    private String content;

    @ApiModelProperty("试看内容")
    private String trialContent;

    @ApiModelProperty("是否允许复制（0：否，1：是）")
    private Boolean allowCopy;

    @ApiModelProperty("是否免费")
    private Boolean free;

    @ApiModelProperty("原价")
    private Double originalPrice;

    @ApiModelProperty("售价")
    private Double salePrice;

    @ApiModelProperty("是否允许打赏（0：否，1：是）")
    private Boolean allowAward;

    @ApiModelProperty("打赏限额（元）")
    private Double awardLimit;

    @ApiModelProperty("上架状态（字典publish_status）")
    private String publishStatus;

    @ApiModelProperty("定时上架时间（仅定时上架有效）")
    private Date publishFutureTime;

    @ApiModelProperty("是否隐藏（0：否，1：是）")
    private Boolean hidden;

    @ApiModelProperty("是否停售（0：否，1：是）")
    private Boolean haltSales;

    @ApiModelProperty("自动审核不通过（0：否，1：是）")
    private Boolean autoDisapproval;

    @ApiModelProperty("人工审核不通过（0：否，1：是）")
    private Boolean manualDisapproval;

    @ApiModelProperty("阅读数")
    private Integer viewCount;

    @ApiModelProperty("点赞数")
    private Integer laudedCount;

    @ApiModelProperty("评论数")
    private Integer commentCount;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("摘要")
    private String summary;

    private static final long serialVersionUID = 1L;

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType == null ? null : infoType.trim();
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl == null ? null : bannerUrl.trim();
    }

    public String getBannerDetailUrl() {
        return bannerDetailUrl;
    }

    public void setBannerDetailUrl(String bannerDetailUrl) {
        this.bannerDetailUrl = bannerDetailUrl == null ? null : bannerDetailUrl.trim();
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    public void setFirstUrl(String firstUrl) {
        this.firstUrl = firstUrl == null ? null : firstUrl.trim();
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl == null ? null : vedioUrl.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTrialContent() {
        return trialContent;
    }

    public void setTrialContent(String trialContent) {
        this.trialContent = trialContent == null ? null : trialContent.trim();
    }

    public Boolean getAllowCopy() {
        return allowCopy;
    }

    public void setAllowCopy(Boolean allowCopy) {
        this.allowCopy = allowCopy;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Boolean getAllowAward() {
        return allowAward;
    }

    public void setAllowAward(Boolean allowAward) {
        this.allowAward = allowAward;
    }

    public Double getAwardLimit() {
        return awardLimit;
    }

    public void setAwardLimit(Double awardLimit) {
        this.awardLimit = awardLimit;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus == null ? null : publishStatus.trim();
    }

    public Date getPublishFutureTime() {
        return publishFutureTime;
    }

    public void setPublishFutureTime(Date publishFutureTime) {
        this.publishFutureTime = publishFutureTime;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getHaltSales() {
        return haltSales;
    }

    public void setHaltSales(Boolean haltSales) {
        this.haltSales = haltSales;
    }

    public Boolean getAutoDisapproval() {
        return autoDisapproval;
    }

    public void setAutoDisapproval(Boolean autoDisapproval) {
        this.autoDisapproval = autoDisapproval;
    }

    public Boolean getManualDisapproval() {
        return manualDisapproval;
    }

    public void setManualDisapproval(Boolean manualDisapproval) {
        this.manualDisapproval = manualDisapproval;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLaudedCount() {
        return laudedCount;
    }

    public void setLaudedCount(Integer laudedCount) {
        this.laudedCount = laudedCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}