/**
 * create by jianglingfeng
 * @date 2018-10
 */
package com.sida.dcloud.content.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class SysUserContent extends BaseEntity implements Serializable {
    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("别名")
    private String alias;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("生效日期")
    private Date validDate;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("锁定状态（0.未锁定 1.已锁定）")
    private Boolean locked;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("国籍(0-中国大陆，1-港／澳／台，2-外籍，3-其他)")
    private String nationality;

    @ApiModelProperty("证件类型(0-身份证；1-军官证；2-护照；3-其他)")
    private String idType;

    @ApiModelProperty("证件号码")
    private String idNum;

    @ApiModelProperty("性别(0,男，1女)")
    private String sex;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("固定电话")
    private String tel;

    @ApiModelProperty("qq号")
    private String qq;

    @ApiModelProperty("微信号")
    private String wechat;

    private static final long serialVersionUID = 1L;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum == null ? null : idNum.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }
}