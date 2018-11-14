/**
 * create by user
 * @date 2018-11
 */
package com.sida.dcloud.content.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class InformationVo extends UserCentricDTO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("资讯标题")
    private String title;

    @ApiModelProperty("资讯封面图片（七牛云）")
    private String bannerUrl;

    @ApiModelProperty("阅读数")
    private Integer viewCount;

    @ApiModelProperty("点赞数")
    private Integer laudedCount;

    @ApiModelProperty("评论数")
    private Integer commentCount;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
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
}