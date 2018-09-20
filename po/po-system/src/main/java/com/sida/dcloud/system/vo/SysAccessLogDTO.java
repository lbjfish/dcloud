package com.sida.dcloud.system.vo;

import com.sida.xiruo.po.common.PageParam;
import io.swagger.annotations.ApiModelProperty;

public class SysAccessLogDTO extends PageParam {

    @ApiModelProperty("选填：开始时间")
    private String startTime;
    @ApiModelProperty("选填：结束时间")
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
