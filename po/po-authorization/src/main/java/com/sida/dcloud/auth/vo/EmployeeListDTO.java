package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by xieguopei on 2017/9/5.
 * 员工管理列表
 */
public class EmployeeListDTO {
    @ApiModelProperty("员工id")
    private String empId;
    @ApiModelProperty("员工工号")
    private String workCard;
    @ApiModelProperty("员工姓名")
    private String empName;
    @ApiModelProperty("员工部门")
    private String empOrg;
    @ApiModelProperty("员工职位")
    private String empPos;
    @ApiModelProperty("员工性别")
    private Integer sex;



    @ApiModelProperty("身份证号")
    private String idCardNumber;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getWorkCard() {
        return workCard;
    }

    public void setWorkCard(String workCard) {
        this.workCard = workCard;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpOrg() {
        return empOrg;
    }

    public void setEmpOrg(String empOrg) {
        this.empOrg = empOrg;
    }

    public String getEmpPos() {
        return empPos;
    }

    public void setEmpPos(String empPos) {
        this.empPos = empPos;
    }
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }
}
