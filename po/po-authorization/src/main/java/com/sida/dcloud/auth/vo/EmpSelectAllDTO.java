package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class EmpSelectAllDTO implements Serializable {
    @ApiModelProperty("通用字段：主键id")
    private String id;

    @ApiModelProperty("员工姓名")
    private String name;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("岗位名称")
    private String positionName;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("组织ID路径")
    private String orgPath;

    @ApiModelProperty("身份证号码")
    private String idcardNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }
}