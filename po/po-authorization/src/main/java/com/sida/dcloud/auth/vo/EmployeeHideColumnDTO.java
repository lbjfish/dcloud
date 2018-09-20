package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by xieguopei on 2017/9/5.
 */
public class EmployeeHideColumnDTO {
    @ApiModelProperty("隐藏列编码")
    private String code;
    @ApiModelProperty("隐藏列名字")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
