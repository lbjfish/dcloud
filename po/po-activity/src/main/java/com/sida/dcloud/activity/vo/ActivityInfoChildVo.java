package com.sida.dcloud.activity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ActivityInfoChildVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("海报图片（七牛云）")
    private String bannerUrl;

    @ApiModelProperty(value = "活动标题", example = "大展")
    private String title;

    @ApiModelProperty("活动摘要")
    private String summary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
