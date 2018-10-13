package com.sida.dcloud.operation.dto;

import com.sida.xiruo.po.common.BaseEntity;
import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

public class CommonUserOperation extends BaseEntity {
    @ApiModelProperty(name = "id", example = "200")
    private String id;
    @ApiModelProperty(name = "第三方登录来源", example = "1（微信），2（支付宝），3（QQ）")
    private String loginFrom;
    @ApiModelProperty(name = "微信号", example = "xiaosage")
    private String wechat;
    @ApiModelProperty(name = "支付宝账号", example = "xiaosage")
    private String alipayNo;
    @ApiModelProperty(name = "qq号", example = "10000")
    private String qq;
    @ApiModelProperty(name = "新浪微博", example = "xiaosage")
    private String sinaWeibo;
    @ApiModelProperty(name = "脸书好", example = "xiaosage")
    private String facebookNo;
    @ApiModelProperty(name = "推特号", example = "xiaosage")
    private String twitterNo;
    @ApiModelProperty(name = "名称", example = "潇洒哥")
    private String name;
    @ApiModelProperty(name = "头像地址", example = "http://www.xxx.com/wwwxx.jpg")
    private String ownerUrl;
    @ApiModelProperty(name = "性别", example = "0（男），1（女）")
    private String sex;
    @ApiModelProperty(name = "出生年月日", example = "19901001")
    private String birthday;
    @ApiModelProperty(name = "自我介绍", example = "我是潇洒哥，擅长写bug")
    private String remark;
    @ApiModelProperty(name = "手机号码", example = "13838383838")
    private String mobile;
    @ApiModelProperty(name = "短信验证码", example = "12345")
    private String verifyCode;
    @ApiModelProperty(name = "密码", example = "123321")
    private String password;

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSinaWeibo() {
        return sinaWeibo;
    }

    public void setSinaWeibo(String sinaWeibo) {
        this.sinaWeibo = sinaWeibo;
    }

    public String getFacebookNo() {
        return facebookNo;
    }

    public void setFacebookNo(String facebookNo) {
        this.facebookNo = facebookNo;
    }

    public String getTwitterNo() {
        return twitterNo;
    }

    public void setTwitterNo(String twitterNo) {
        this.twitterNo = twitterNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(String loginFrom) {
        this.loginFrom = loginFrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public void setOwnerUrl(String ownerUrl) {
        this.ownerUrl = ownerUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
