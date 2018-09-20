package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by xieguopei on 2017/9/5.
 */
public class EmployeeHideAddDTO {
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("隐藏列")
    private List<EmployeeHideColumnDTO> list;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<EmployeeHideColumnDTO> getList() {
        return list;
    }

    public void setList(List<EmployeeHideColumnDTO> list) {
        this.list = list;
    }
}
