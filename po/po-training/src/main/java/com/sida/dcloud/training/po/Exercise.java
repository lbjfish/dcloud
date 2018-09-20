/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class Exercise extends BaseEntity implements Serializable {
    @ApiModelProperty("所属地区")
    private String regionId;

    @ApiModelProperty("题型")
    private String type;

    @ApiModelProperty("表现形式")
    private String style;

    @ApiModelProperty("章节id")
    private String sectionId;

    @ApiModelProperty("微课编码")
    private String microcourseCode;

    @ApiModelProperty("图片url地址")
    private String url;

    @ApiModelProperty("难度")
    private String difficulty;

    @ApiModelProperty("分值")
    private Integer score;

    @ApiModelProperty("题目")
    private String question;

    @ApiModelProperty("题干")
    private String items;

    @ApiModelProperty("正确答案")
    private String answer;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty("顺序号")
    private Integer sequence;

    @ApiModelProperty("单个分组")
    private String groupId;

    @ApiModelProperty("多个分组")
    private List<String> groupIds;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    private static final long serialVersionUID = 1L;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style == null ? null : style.trim();
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId == null ? null : sectionId.trim();
    }

    public String getMicrocourseCode() {
        return microcourseCode;
    }

    public void setMicrocourseCode(String microcourseCode) {
        this.microcourseCode = microcourseCode == null ? null : microcourseCode.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty == null ? null : difficulty.trim();
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
        this.question = question == null ? null : question.trim();
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items == null ? null : items.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}