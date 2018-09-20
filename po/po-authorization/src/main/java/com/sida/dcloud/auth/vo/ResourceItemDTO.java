package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 页面资源dto
 */
public class ResourceItemDTO {

    @ApiModelProperty("资源名称")
    private String name;
    @ApiModelProperty("资源编码（前端id）")
    private String code;
    @ApiModelProperty("是否隐藏：true:是，false:否")
    private boolean hiddenFlag = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isHiddenFlag() {
        return hiddenFlag;
    }

    public void setHiddenFlag(boolean hiddenFlag) {
        this.hiddenFlag = hiddenFlag;
    }
}
