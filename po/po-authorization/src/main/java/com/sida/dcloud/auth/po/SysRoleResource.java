/**
 * create by Administrator
 * @date 2017-08
 */
package com.sida.dcloud.auth.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SysRoleResource extends BaseEntity implements Serializable {
    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("资源id")
    private String resourceId;

    @ApiModelProperty("字段权限标识（暂未定义:类似于“不可见”“可见”“可读”等字段权限划分）")
    private String identifier;

    private static final long serialVersionUID = 1L;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier == null ? null : identifier.trim();
    }
}