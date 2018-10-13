/**
 * create by jianglingfeng
 * @date 2018-10
 */
package com.sida.dcloud.operation.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class CommonUser extends BaseEntity implements Serializable {
    @ApiModelProperty("是否审核（0. 否  1. 是）")
    private Boolean approvalStatus;

    @ApiModelProperty("是否会员（0. 否  1. 是）")
    private Boolean memberStatus;

    @ApiModelProperty("有效起始时间")
    private Date startTime;

    @ApiModelProperty("有效过期时间")
    private Date expiredTime;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("昵称")
    private String alias;

    @ApiModelProperty("所属地区（关联system.sys_region区域表的id）")
    private String regionId;

    @ApiModelProperty("性别(0,男，1女)")
    private String sex;

    @ApiModelProperty("出生日期（YYYYMMDD)")
    private String birthday;

    @ApiModelProperty("用户头像（七牛云图片地址）")
    private String ownerUrl;

    @ApiModelProperty("人脸采集（七牛云图片地址）")
    private String faceUrl;

    @ApiModelProperty("国籍（字典）")
    private String nationality;

    @ApiModelProperty("证件类型（字典，1：身份证  2：护照 3：军官证 4：其他)")
    private String cardtype;

    @ApiModelProperty("证件号")
    private String idcard;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("联系地址")
    private String address;

    @ApiModelProperty("邮政编码")
    private String postcode;

    @ApiModelProperty("注册时间")
    private Date regTime;

    @ApiModelProperty("审核通过时间")
    private Date approvalTime;

    @ApiModelProperty("最后登录时间")
    private Date lastLogin;

    @ApiModelProperty("用户类别（字典，1：普通用户，2：设计师）")
    private String userCat;

    @ApiModelProperty("总获读数")
    private Integer readCount;

    @ApiModelProperty("总获赞数")
    private Integer laudedCount;

    @ApiModelProperty("总获评数")
    private Integer commentedCount;

    @ApiModelProperty("总获关注数")
    private Integer caredCount;

    @ApiModelProperty("发表文章数")
    private Integer titleCount;

    @ApiModelProperty("总评论数")
    private Integer commentCount;

    @ApiModelProperty("总点赞数")
    private Integer laudCount;

    @ApiModelProperty("总关注数")
    private Integer careCount;

    @ApiModelProperty("活跃状态（0.不活跃 1.活跃）")
    private Boolean activeStatus;

    @ApiModelProperty("在线时长（分钟）")
    private Integer onlineMinutes;

    @ApiModelProperty("脸书号")
    private String facebookNo;

    @ApiModelProperty("推特号")
    private String twitterNo;

    @ApiModelProperty("新浪微博")
    private String sinaWeibo;

    @ApiModelProperty("个人主页")
    private String homepage;

    @ApiModelProperty("个人介绍")
    private String introduce;

    @ApiModelProperty("加V级别")
    private Integer vplus;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("支付宝账号")
    private String alipayNo;

    private static final long serialVersionUID = 1L;

    public Boolean getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Boolean getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Boolean memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public void setOwnerUrl(String ownerUrl) {
        this.ownerUrl = ownerUrl == null ? null : ownerUrl.trim();
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl == null ? null : faceUrl.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype == null ? null : cardtype.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUserCat() {
        return userCat;
    }

    public void setUserCat(String userCat) {
        this.userCat = userCat == null ? null : userCat.trim();
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getLaudedCount() {
        return laudedCount;
    }

    public void setLaudedCount(Integer laudedCount) {
        this.laudedCount = laudedCount;
    }

    public Integer getCommentedCount() {
        return commentedCount;
    }

    public void setCommentedCount(Integer commentedCount) {
        this.commentedCount = commentedCount;
    }

    public Integer getCaredCount() {
        return caredCount;
    }

    public void setCaredCount(Integer caredCount) {
        this.caredCount = caredCount;
    }

    public Integer getTitleCount() {
        return titleCount;
    }

    public void setTitleCount(Integer titleCount) {
        this.titleCount = titleCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLaudCount() {
        return laudCount;
    }

    public void setLaudCount(Integer laudCount) {
        this.laudCount = laudCount;
    }

    public Integer getCareCount() {
        return careCount;
    }

    public void setCareCount(Integer careCount) {
        this.careCount = careCount;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Integer getOnlineMinutes() {
        return onlineMinutes;
    }

    public void setOnlineMinutes(Integer onlineMinutes) {
        this.onlineMinutes = onlineMinutes;
    }

    public String getFacebookNo() {
        return facebookNo;
    }

    public void setFacebookNo(String facebookNo) {
        this.facebookNo = facebookNo == null ? null : facebookNo.trim();
    }

    public String getTwitterNo() {
        return twitterNo;
    }

    public void setTwitterNo(String twitterNo) {
        this.twitterNo = twitterNo == null ? null : twitterNo.trim();
    }

    public String getSinaWeibo() {
        return sinaWeibo;
    }

    public void setSinaWeibo(String sinaWeibo) {
        this.sinaWeibo = sinaWeibo == null ? null : sinaWeibo.trim();
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage == null ? null : homepage.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public Integer getVplus() {
        return vplus;
    }

    public void setVplus(Integer vplus) {
        this.vplus = vplus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo == null ? null : alipayNo.trim();
    }
}