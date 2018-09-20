package com.sida.dcloud.auth.po;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class MyUser  {

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户名")
    private String name;

    private List<MyUser> ids;

    private SysButton sysButton;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MyUser> getIds() {
        return ids;
    }

    public void setIds(List<MyUser> ids) {
        this.ids = ids;
    }

    public SysButton getSysButton() {
        return sysButton;
    }

    public void setSysButton(SysButton sysButton) {
        this.sysButton = sysButton;
    }
}