package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 员工对象集合
 */
public class EmpDTO{
    @ApiModelProperty("员工id")
    private String empId;
    @ApiModelProperty("员工姓名")
    private String empName;
    @ApiModelProperty("岗位名称")
    private String positionName;
    @ApiModelProperty("带教类型id")
    private String techTypeId;
    @ApiModelProperty("带教类型名称")
    private String techType;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getTechTypeId() {
        return techTypeId;
    }

    public void setTechTypeId(String techTypeId) {
        this.techTypeId = techTypeId;
    }

    public String getTechType() {
        return techType;
    }

    public void setTechType(String techType) {
        this.techType = techType;
    }
}
