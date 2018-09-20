/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class ExamAnswerTrack extends BaseEntity implements Serializable {
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("考试日志id")
    private String examId;

    @ApiModelProperty("习题id")
    private String exerciseId;

    @ApiModelProperty("正确答案")
    private String rightAnswer;

    @ApiModelProperty("答案")
    private String answer;

    @ApiModelProperty("答题时间")
    private Date thatTime;

    @ApiModelProperty("顺序号")
    private Integer sequence;

    @ApiModelProperty("有变动，已答题")
    private boolean dirty;

    @ApiModelProperty("考试试卷")
    private ExamTrack examTrack;

    private static final long serialVersionUID = 1L;

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public ExamTrack getExamTrack() {
        return examTrack;
    }

    public void setExamTrack(ExamTrack examTrack) {
        this.examTrack = examTrack;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId == null ? null : examId.trim();
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}