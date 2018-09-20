/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ExamNumRule extends BaseEntity implements Serializable {
    @ApiModelProperty("所属科目")
    private String subject;

    @ApiModelProperty("总题量")
    private Integer exerciseSum;

    @ApiModelProperty("合格分数")
    private Integer qualifiedScore;

    @ApiModelProperty("考试时间（分钟）")
    private Integer examMinutes;

    private static final long serialVersionUID = 1L;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public Integer getExerciseSum() {
        return exerciseSum;
    }

    public void setExerciseSum(Integer exerciseSum) {
        this.exerciseSum = exerciseSum;
    }

    public Integer getQualifiedScore() {
        return qualifiedScore;
    }

    public void setQualifiedScore(Integer qualifiedScore) {
        this.qualifiedScore = qualifiedScore;
    }

    public Integer getExamMinutes() {
        return examMinutes;
    }

    public void setExamMinutes(Integer examMinutes) {
        this.examMinutes = examMinutes;
    }
}