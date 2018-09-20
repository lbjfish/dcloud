package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

public class AppUserInfoDTO {

    @ApiModelProperty("用户id")
    private String id;
    //优先从用户表获取
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机")
    private String mobile;
    @ApiModelProperty("固定电话")
    private String tel;
    @ApiModelProperty("性别(0,男，1女)")
    private String sex;

    //然后从员工表补充
    @ApiModelProperty("员工头像（文件路径）")
    private String photo;
    @ApiModelProperty("工号")
    private String workCard;
    @ApiModelProperty("公司名")
    private String companyName;
    @ApiModelProperty("部门名称")
    private String deptName;
    @ApiModelProperty("岗位名称")
    private String positionName;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getWorkCard() {
        return workCard;
    }

    public void setWorkCard(String workCard) {
        this.workCard = workCard;
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
