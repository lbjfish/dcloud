package com.sida.dcloud.auth.vo;

import com.sida.xiruo.po.common.PageParam;
import io.swagger.annotations.ApiModelProperty;

public class RoleListParam extends PageParam {

    @ApiModelProperty(value = "角色名称模糊查询")
    private String roleLike;
    @ApiModelProperty(value = "停启用状态（true:停用 false:启用）", example = "false")
    private Boolean statusEqual;

    public String getRoleLike() {
        return roleLike;
    }

    public void setRoleLike(String roleLike) {
        this.roleLike = roleLike;
    }

    public Boolean getStatusEqual() {
        return statusEqual;
    }

    public void setStatusEqual(Boolean statusEqual) {
        this.statusEqual = statusEqual;
    }

    @Override
    public String toString() {
        return "RoleListParam{" +
                super.toString() +
                "roleLike='" + roleLike + '\'' +
                ", statusEqual=" + statusEqual +
                '}';
    }
}
