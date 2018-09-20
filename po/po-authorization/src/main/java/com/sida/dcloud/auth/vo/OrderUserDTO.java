package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysUser;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 订单用户DTO
 */
public class OrderUserDTO {

    /******************************入参*************************************/
    @ApiModelProperty("必填：操作类型（0-新增 1-更新）")
    private Integer action;
    @ApiModelProperty("必填：证件类型(0-身份证；1-军官证；2-护照；3-其他)")
    private String idType;
    @ApiModelProperty("必填：证件号码")
    private String idNum;
    @ApiModelProperty("国籍(0-中国大陆，1-港／澳／台，2-外籍，3-其他)")
    private String nationality;
    @ApiModelProperty("性别(0,男，1女)")
    private String sex;
    @ApiModelProperty("生日")
    private Date birthday;
    @ApiModelProperty("新增必填：用户名")
    private String name;
    @ApiModelProperty("新增必填：手机号")
    private String mobile;
    @ApiModelProperty("固定电话")
    private String tel;
    @ApiModelProperty("qq号")
    private String qq;
    @ApiModelProperty("微信号")
    private String wechat;


    /*******************************回参**************************************/
    @ApiModelProperty("该订单用户对应用户id，新增时返回字段，更新时是入参字段，有营销模块带入")
    private String userId;
    @ApiModelProperty("该订单用户对应用户对象")
    private SysUser sysUser;

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
