package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class UserListDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("停用状态（false.启用 true.停用）")
    private Boolean status;

    @ApiModelProperty("通用字段：创建时间")
    private Date createdAt;

    @ApiModelProperty("用户角色")
    private String roleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
