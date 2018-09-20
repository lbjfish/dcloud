package com.sida.dcloud.system.vo;

import com.sida.dcloud.system.po.SysTrainingfield;
import io.swagger.annotations.ApiModelProperty;

public class SysTrainingfieldVo extends SysTrainingfield{

    @ApiModelProperty("培训车型")
    private String carModels;

    public String getCarModels() {
        return carModels;
    }

    public void setCarModels(String carModels) {
        this.carModels = carModels;
    }
}
