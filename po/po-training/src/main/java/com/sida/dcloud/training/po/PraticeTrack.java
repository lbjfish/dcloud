/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class PraticeTrack extends BaseEntity implements Serializable {
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("练习类型（字典：1. 顺序练习，2. 章节练习，3. 题型练习，4. 分组练习）")
    private String praticeType;

    @ApiModelProperty("章节id")
    private String sectionId;

    @ApiModelProperty("题型")
    private String exerciseType;

    @ApiModelProperty("表现形式")
    private String style;

    @ApiModelProperty("专项分组id")
    private String groupId;

    @ApiModelProperty("习题id")
    private String exerciseId;

    @ApiModelProperty("正确答案")
    private String rightAnswer;

    @ApiModelProperty("答案")
    private String answer;

    @ApiModelProperty("练习时间")
    private Date thatTime;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPraticeType() {
        return praticeType;
    }

    public void setPraticeType(String praticeType) {
        this.praticeType = praticeType == null ? null : praticeType.trim();
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId == null ? null : sectionId.trim();
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType == null ? null : exerciseType.trim();
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style == null ? null : style.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId == null ? null : exerciseId.trim();
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer == null ? null : rightAnswer.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Date getThatTime() {
        return thatTime;
    }

    public void setThatTime(Date thatTime) {
        this.thatTime = thatTime;
    }
}