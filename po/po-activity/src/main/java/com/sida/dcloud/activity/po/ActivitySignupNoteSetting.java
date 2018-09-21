/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ActivitySignupNoteSetting extends BaseEntity implements Serializable {
    @ApiModelProperty("字段名称")
    private String name;

    @ApiModelProperty("字段编码")
    private String code;

    @ApiModelProperty("字段显示名（初始等于名称，可修改，最终呈现在页面上的名称）")
    private String displayName;

    @ApiModelProperty("是否隐藏（0：否，1：是）")
    private Boolean hideStatus;

    @ApiModelProperty("允许为空（0：否，1：是）")
    private Boolean allowEmpty;

    @ApiModelProperty("输入长度限制")
    private Integer sizeLimit;

    @ApiModelProperty("校验正则表达式")
    private String vRegexp;

    @ApiModelProperty("排序值（越小越靠前）")
    private Integer sort;

    @ApiModelProperty("版本（YYYYMMDD）")
    private String version;

    private static final long serialVersionUID = 1L;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    public Boolean getHideStatus() {
        return hideStatus;
    }

    public void setHideStatus(Boolean hideStatus) {
        this.hideStatus = hideStatus;
    }

    public Boolean getAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(Boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public Integer getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(Integer sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public String getvRegexp() {
        return vRegexp;
    }

    public void setvRegexp(String vRegexp) {
        this.vRegexp = vRegexp == null ? null : vRegexp.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }
}