package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

public class RoleDTO {

    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色编码")
    private String roleCode;
    @ApiModelProperty("角色名称")
    private String roleName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
