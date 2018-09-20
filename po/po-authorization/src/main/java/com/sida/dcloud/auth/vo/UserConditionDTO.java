package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wuzhenwei on 2017/9/5.
 */
public class UserConditionDTO{
    @ApiModelProperty("角色编码")
    private String roleCode;
    @ApiModelProperty("所在部门的id")
    private String organizationId;
    @ApiModelProperty("发起人用户id")
    private String userId;


    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
