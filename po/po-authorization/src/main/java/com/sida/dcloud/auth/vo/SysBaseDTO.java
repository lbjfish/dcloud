package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

public class SysBaseDTO {

    @ApiModelProperty("权限标识:true 启用数据权限；false 停用数据权限；默认启用")
    private Boolean filterFlag;

    public Boolean getFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(Boolean filterFlag) {
        this.filterFlag = filterFlag;
    }
}
