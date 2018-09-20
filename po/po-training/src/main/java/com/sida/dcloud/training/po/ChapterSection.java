/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ChapterSection extends BaseEntity implements Serializable {

    @ApiModelProperty("章节名")
    private String name;

    @ApiModelProperty("章节编码")
    private String code;

    @ApiModelProperty("科目")
    private String subject;

    @ApiModelProperty("章节下的题量")
    private Integer total;

    @ApiModelProperty("排序标记")
    private Integer sort;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty("增长量")
    private Integer increase;

    @ApiModelProperty("属于这个章节的题目集合")
    private List<Exercise> exerciseList;

    private static final long serialVersionUID = 1L;

    public Integer getIncrease() {
        return increase;
    }

    public void setIncrease(Integer increase) {
        this.increase = increase;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}