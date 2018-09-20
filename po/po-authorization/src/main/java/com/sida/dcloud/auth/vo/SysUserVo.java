package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysUser;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SysUserVo extends SysUser{

    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("登录账号")
    private String userAccount;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("角色编码")
    private String roleCode;

    //拓展多角色
    @ApiModelProperty("角色组")
    private List<RoleDTO> roleList;

    //组织信息
    @ApiModelProperty("所在组织id")
    private String organizationId;
    @ApiModelProperty("所在组织path")
    private String organizationPath;
    @ApiModelProperty("所处片区id，没有则为空")
    private String areaId;
    @ApiModelProperty("所处门店id，没有则为空")
    private String storeId;
    @ApiModelProperty("所处牌证部门id，没有则为空")
    private String certId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getOrganizationPath() {
        return organizationPath;
    }

    public void setOrganizationPath(String organizationPath) {
        this.organizationPath = organizationPath;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public List<RoleDTO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDTO> roleList) {
        this.roleList = roleList;
    }
}
