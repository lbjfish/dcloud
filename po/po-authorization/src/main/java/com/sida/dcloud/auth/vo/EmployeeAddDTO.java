package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by xieguopei on 2017/9/5.
 */
public class EmployeeAddDTO {
    @ApiModelProperty("员工id")
    private String id;
    @ApiModelProperty("员工姓名")
    private String empName;
    @ApiModelProperty("工号")
    private String workCard;
    @ApiModelProperty("身份证号")
    private String idNumber;
    @ApiModelProperty("出生日期")
    private String birthday;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("民族")
    private String nation;
    @ApiModelProperty("身份证地址")
    private String address;
    @ApiModelProperty("身份证发证机关")
    private String authority;
    @ApiModelProperty("身份证有效期开始时间")
    private String beginTime;
    @ApiModelProperty("身份证有效期结束时间")
    private String endTime;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("组织id")
    private String orgId;
    @ApiModelProperty("岗位id")
    private String posId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getWorkCard() {
        return workCard;
    }

    public void setWorkCard(String workCard) {
        this.workCard = workCard;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }
}
