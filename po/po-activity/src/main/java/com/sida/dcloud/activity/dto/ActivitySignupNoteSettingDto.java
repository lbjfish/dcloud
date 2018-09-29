package com.sida.dcloud.activity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ActivitySignupNoteSettingDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("字段编码（驼峰名称）")
    private String code;
    @ApiModelProperty("字段显示名")
    private String displayName;
    @ApiModelProperty("是否允许空（0：否，1：是）")
    private Boolean allowEmpty;
    @ApiModelProperty("输入长度限制")
    private Integer sizeLimit;
    @ApiModelProperty("校验正则表达式")
    private Integer vRegexp;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public Integer getvRegexp() {
        return vRegexp;
    }

    public void setvRegexp(Integer vRegexp) {
        this.vRegexp = vRegexp;
    }
}
