package com.sida.dcloud.training.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class IconGroupVo extends UserCentricDTO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("创建用户id")
    private String createdUser;
    @ApiModelProperty("创建用户名称")
    private String createdUserName;
    @ApiModelProperty("创建时间")
    private Date createdAt;
    @ApiModelProperty("排序标志")
    private int sort;

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
