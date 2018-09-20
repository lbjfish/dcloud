package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysPosition;
import io.swagger.annotations.ApiModelProperty;

public class SysPositionVo extends SysPosition{

    @ApiModelProperty("员工数量")
    private Long empNum;

    public Long getEmpNum() {
        return empNum;
    }

    public void setEmpNum(Long empNum) {
        this.empNum = empNum;
    }
}
