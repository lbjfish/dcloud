package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 密码重置dto
 */
public class PasswordResetDTO {

    @ApiModelProperty("新密码")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
