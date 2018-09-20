package com.sida.dcloud.system.vo;

import com.sida.dcloud.system.po.SysAppFunction;
import com.sida.dcloud.system.po.SysAppFunctionGroup;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SysAppFunctionGroupVo extends SysAppFunctionGroup {
    @ApiModelProperty("功能列表")
    private List<SysAppFunction>  functionItemList;

    public List<SysAppFunction> getFunctionItemList() {
        return functionItemList;
    }

    public void setFunctionItemList(List<SysAppFunction> functionItemList) {
        this.functionItemList = functionItemList;
    }
}
