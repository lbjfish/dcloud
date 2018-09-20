package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysEmployee;
import io.swagger.annotations.ApiModelProperty;

public class SysEmployeeVo extends SysEmployee{

    @ApiModelProperty("公司名")
    private String companyName;
    @ApiModelProperty("部门id")
    private String deptId;
    @ApiModelProperty("部门名称")
    private String deptName;
    @ApiModelProperty("岗位名称")
    private String positionName;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
