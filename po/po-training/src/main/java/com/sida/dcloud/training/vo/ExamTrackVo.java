package com.sida.dcloud.training.vo;

import com.sida.xiruo.po.common.PageParam;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ExamTrackVo extends PageParam {
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
    @ApiModelProperty("所属科目")
    private String subject;
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("得分")
    private Integer score;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
