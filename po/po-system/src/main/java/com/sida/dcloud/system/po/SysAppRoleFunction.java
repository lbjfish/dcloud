/**
 * create by Administrator
 * @date 2018-02
 */
package com.sida.dcloud.system.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SysAppRoleFunction extends BaseEntity implements Serializable {
    @ApiModelProperty("")
    private String id;

    @ApiModelProperty("分组id")
    private String groupId;

    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("功能id")
    private String functionId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId == null ? null : functionId.trim();
    }
}