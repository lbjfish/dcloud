/**
 * create by Administrator
 * @date 2017-08
 */
package com.sida.dcloud.auth.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SysUserRole extends BaseEntity implements Serializable {
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("角色id")
    private String roleId;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}