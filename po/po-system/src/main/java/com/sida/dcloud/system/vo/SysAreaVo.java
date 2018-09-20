package com.sida.dcloud.system.vo;

import com.sida.dcloud.system.po.SysArea;
import io.swagger.annotations.ApiModelProperty;

public class SysAreaVo extends SysArea{

    @ApiModelProperty("门店数量")
    private Long storeNum;

    public Long getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(Long storeNum) {
        this.storeNum = storeNum;
    }
}
