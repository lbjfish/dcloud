/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ExamSectionRule extends BaseEntity implements Serializable {
    @ApiModelProperty("章节id")
    private String sectionId;

    @ApiModelProperty("百分比")
    private Integer rate;

    private static final long serialVersionUID = 1L;

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId == null ? null : sectionId.trim();
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}