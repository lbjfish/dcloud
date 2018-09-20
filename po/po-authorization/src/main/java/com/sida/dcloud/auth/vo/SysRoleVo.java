package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysRole;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SysRoleVo extends SysRole{

    @ApiModelProperty("权限列表")
    private List<SysResourceVo> resources;

    @ApiModelProperty("资源id列表")
    private List<String> resourceIds;

    public List<SysResourceVo> getResources() {
        return resources;
    }

    public void setResources(List<SysResourceVo> resources) {
        this.resources = resources;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
