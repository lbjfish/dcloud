package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysResource;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SysResourceVo extends SysResource{

    @ApiModelProperty("权限标识：“NO”表示不具备此权限，“YES”表示具备此权限")
    private String identifier;

    @ApiModelProperty("子资源")
    List<SysResourceVo> children = Lists.newArrayList();

    private String roleIds;

    private List<String> roleIdList;

    private String roleNames;

    private List<String> roleNameList;

    //父资源code：获取页面静态资源专用
    private String parentCode;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<SysResourceVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysResourceVo> children) {
        this.children = children;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public List<String> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public SysResourceVo() {
    }

    public SysResourceVo(String name, String code, String href, String parentId, Integer sort, String value, String type) {
        super(name,code,href,parentId,sort,value,type);
    }
}
