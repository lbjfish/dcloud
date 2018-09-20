/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ExamTypeRule extends BaseEntity implements Serializable {
    @ApiModelProperty("题型")
    private String type;

    @ApiModelProperty("所属科目")
    private String subject;

    @ApiModelProperty("百分比")
    private Integer rate;

    private static final long serialVersionUID = 1L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}