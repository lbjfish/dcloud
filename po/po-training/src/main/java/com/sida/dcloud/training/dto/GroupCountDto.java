package com.sida.dcloud.training.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GroupCountDto implements Serializable {
    @ApiModelProperty("组id")
    private String groupId;
    @ApiModelProperty("数量")
    private int count;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
