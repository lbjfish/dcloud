/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.operation.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class CompanyInfo extends BaseEntity implements Serializable {
    @ApiModelProperty("通用字段：禁用标识（0.可用  1.禁用）")
    private Boolean disable;

    @ApiModelProperty("排序值（数字小的排前面）")
    private Integer sort;

    @ApiModelProperty("企业名称")
    private String name;

    @ApiModelProperty("企业编码")
    private String code;

    @ApiModelProperty("企业简称")
    private String shortName;

    @ApiModelProperty("英文名")
    private String engName;

    @ApiModelProperty("英文简称")
    private String engShortName;

    @ApiModelProperty("所在地区")
    private String regionId;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("法人")
    private String legalRepresentative;

    @ApiModelProperty("联系人")
    private String linkMan;

    @ApiModelProperty("固定电话")
    private String phone;

    @ApiModelProperty("移动电话")
    private String mobile;

    @ApiModelProperty("电子邮件地址")
    private String email;

    @ApiModelProperty("企业官网网址")
    private String homepage;

    @ApiModelProperty("成立时间")
    private Date foundDate;

    @ApiModelProperty("公司规模（员工数量）")
    private String personTotal;

    @ApiModelProperty("企业图标（七牛云图片地址）")
    private String logoUrl;

    @ApiModelProperty("企业类别（字典，1：工业企业，2：设计公司）")
    private String companyCat;

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

    @ApiModelProperty("加V级别")
    private Integer vplus;

    @ApiModelProperty("主攻领域")
    private String majorScope;

    @ApiModelProperty("奖项")
    private String honordItems;

    @ApiModelProperty("企业简介")
    private String brief;

    @ApiModelProperty("备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName == null ? null : engName.trim();
    }

    public String getEngShortName() {
        return engShortName;
    }

    public void setEngShortName(String engShortName) {
        this.engShortName = engShortName == null ? null : engShortName.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage == null ? null : homepage.trim();
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getPersonTotal() {
        return personTotal;
    }

    public void setPersonTotal(String personTotal) {
        this.personTotal = personTotal == null ? null : personTotal.trim();
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }

    public String getCompanyCat() {
        return companyCat;
    }

    public void setCompanyCat(String companyCat) {
        this.companyCat = companyCat == null ? null : companyCat.trim();
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

    public Integer getVplus() {
        return vplus;
    }

    public void setVplus(Integer vplus) {
        this.vplus = vplus;
    }

    public String getMajorScope() {
        return majorScope;
    }

    public void setMajorScope(String majorScope) {
        this.majorScope = majorScope == null ? null : majorScope.trim();
    }

    public String getHonordItems() {
        return honordItems;
    }

    public void setHonordItems(String honordItems) {
        this.honordItems = honordItems == null ? null : honordItems.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}