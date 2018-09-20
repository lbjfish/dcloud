package com.sida.dcloud.training.vo;

import com.sida.xiruo.po.common.PageParam;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class FavoriteNoteVo extends PageParam {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("机构id")
    private String orgId;
    @ApiModelProperty("机构名称")
    private String orgName;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("习题id")
    private String exerciseId;
    @ApiModelProperty("题目")
    private String question;
    @ApiModelProperty("收藏时间")
    private Date thatTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getThatTime() {
        return thatTime;
    }

    public void setThatTime(Date thatTime) {
        this.thatTime = thatTime;
    }
}
