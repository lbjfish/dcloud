package com.sida.dcloud.training.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ExerciseVo extends UserCentricDTO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("所属地区id")
    private String regionId;
    @ApiModelProperty("所属地区名称")
    private String regionName;
    @ApiModelProperty("题型")
    private String type;
    @ApiModelProperty("表现形式")
    private String style;
    @ApiModelProperty("章节id")
    private String sectionId;
    @ApiModelProperty("章节名称")
    private String sectionName;
    @ApiModelProperty("分组ids")
    private String groupIds;
    @ApiModelProperty("分组名称")
    private String groupNames;
    @ApiModelProperty("微课编码")
    private String microcourseCode;
    @ApiModelProperty("难度")
    private String difficulty;
    @ApiModelProperty("分值")
    private Integer score;
    @ApiModelProperty("题目")
    private String question;
    @ApiModelProperty("创建用户id")
    private String createdUser;
    @ApiModelProperty("创建用户名称")
    private String createdUserName;
    @ApiModelProperty("创建时间")
    private Date createdAt;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getMicrocourseCode() {
        return microcourseCode;
    }

    public void setMicrocourseCode(String microcourseCode) {
        this.microcourseCode = microcourseCode;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
