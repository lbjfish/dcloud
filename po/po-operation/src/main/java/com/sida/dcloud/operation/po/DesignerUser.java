/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.operation.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class DesignerUser extends BaseEntity implements Serializable {
    @ApiModelProperty("是否认证（0. 否  1. 是）")
    private Boolean auditStatus;

    @ApiModelProperty("是否大师（0. 否  1. 是）")
    private Boolean advancedStatus;

    @ApiModelProperty("影响力指数")
    private Integer powerIndex;

    @ApiModelProperty("头衔")
    private String title;

    @ApiModelProperty("格言")
    private String motto;

    @ApiModelProperty("个人简介")
    private String summary;

    @ApiModelProperty("奖项")
    private String honordItems;

    @ApiModelProperty("排序值（数字小的排前面）")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public Boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Boolean getAdvancedStatus() {
        return advancedStatus;
    }

    public void setAdvancedStatus(Boolean advancedStatus) {
        this.advancedStatus = advancedStatus;
    }

    public Integer getPowerIndex() {
        return powerIndex;
    }

    public void setPowerIndex(Integer powerIndex) {
        this.powerIndex = powerIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto == null ? null : motto.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getHonordItems() {
        return honordItems;
    }

    public void setHonordItems(String honordItems) {
        this.honordItems = honordItems == null ? null : honordItems.trim();
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