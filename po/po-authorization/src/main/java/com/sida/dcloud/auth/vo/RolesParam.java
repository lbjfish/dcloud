package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

public class RolesParam{

    @ApiModelProperty(value = "角色id拼接字符串，英文逗号分隔，结尾不需要','")
    private String roleIds;

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "RolesParam{" +
                "roleIds='" + roleIds + '\'' +
                '}';
    }
}
