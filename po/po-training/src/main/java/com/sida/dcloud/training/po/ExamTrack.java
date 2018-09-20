/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ExamTrack extends BaseEntity implements Serializable {
    @ApiModelProperty("学员id（与用户id相同）")
    private String userId;

    @ApiModelProperty("所属科目")
    private String subject;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("总题量")
    private Integer total;

    @ApiModelProperty("未完成数量")
    private Integer uncomplete;

    @ApiModelProperty("得分")
    private Integer score;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("false: 标记用户答题后已保存，true: 用户答题后未保存")
    private boolean dirty;

    @ApiModelProperty("考试答题日志")
    private Map<String, ExamAnswerTrack> examAnswerTrackMap;

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public Map<String, ExamAnswerTrack> getExamAnswerTrackMap() {
        return examAnswerTrackMap;
    }

    public void setExamAnswerTrackMap(Map<String, ExamAnswerTrack> examAnswerTrackMap) {
        this.examAnswerTrackMap = examAnswerTrackMap;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getUncomplete() {
        return uncomplete;
    }

    public void setUncomplete(Integer uncomplete) {
        this.uncomplete = uncomplete;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}