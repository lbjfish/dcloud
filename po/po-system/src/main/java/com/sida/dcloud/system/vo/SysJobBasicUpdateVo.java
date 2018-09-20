package com.sida.dcloud.system.vo;

import io.swagger.annotations.ApiModelProperty;

public class SysJobBasicUpdateVo {
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("对应值")
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
