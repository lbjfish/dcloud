/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class ErrorNote extends BaseEntity implements Serializable {
    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("习题id")
    private String exerciseId;

    @ApiModelProperty("正确答案")
    private String rightAnswer;

    @ApiModelProperty("答案")
    private String answer;

    @ApiModelProperty("出错时间")
    private Date errorTime;

    @ApiModelProperty("纠错时间")
    private Date rightTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("纠错标志")
    private boolean corrected;

    private static final long serialVersionUID = 1L;

    public boolean isCorrected() {
        return corrected;
    }

    public void setCorrected(boolean corrected) {
        this.corrected = corrected;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public Date getRightTime() {
        return rightTime;
    }

    public void setRightTime(Date rightTime) {
        this.rightTime = rightTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}