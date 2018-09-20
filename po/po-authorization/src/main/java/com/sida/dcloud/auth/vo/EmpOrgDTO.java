package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 员工所属组织
 */
public class EmpOrgDTO {
    @ApiModelProperty("员工id")
    private String empId;
    @ApiModelProperty("员工姓名")
    private String empName;

    @ApiModelProperty("驾校id")
    private String schoolId;
    @ApiModelProperty("片区id")
    private String areaId;
    @ApiModelProperty("门店id")
    private String storeId;
    @ApiModelProperty("跟片区同级的行政部门id")
    private String secondLevelOrgId;

    @ApiModelProperty("获取员工所属责任部门（一般为员工同级或上级组织）")
    private String leaderOrg;
    @ApiModelProperty("获取员工所属责任人")
    private String leaderPerson;

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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSecondLevelOrgId() {
        return secondLevelOrgId;
    }

    public void setSecondLevelOrgId(String secondLevelOrgId) {
        this.secondLevelOrgId = secondLevelOrgId;
    }

    public String getLeaderOrg() {
        return leaderOrg;
    }

    public void setLeaderOrg(String leaderOrg) {
        this.leaderOrg = leaderOrg;
    }

    public String getLeaderPerson() {
        return leaderPerson;
    }

    public void setLeaderPerson(String leaderPerson) {
        this.leaderPerson = leaderPerson;
    }
}
