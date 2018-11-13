package com.sida.dcloud.compensate.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class CompensateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("事务组id")
    private String groupId;
    @ApiModelProperty("补偿请求类型，notify：补偿结果通知，compensate：补偿决策")
    private String action;
    @ApiModelProperty("补偿数据")
    private String json;
    @ApiModelProperty("补偿结果状态")
    private Boolean resState;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Boolean getResState() {
        return resState;
    }

    public void setResState(Boolean resState) {
        this.resState = resState;
    }
}
