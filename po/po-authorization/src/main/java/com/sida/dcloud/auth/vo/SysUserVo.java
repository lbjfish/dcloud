package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysUser;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SysUserVo extends SysUser{

    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("登录账号")
    private String userAccount;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("角色编码")
    private String roleCode;

    //拓展多角色
    @ApiModelProperty("角色组")
    private List<RoleDTO> roleList;

    //组织信息
    @ApiModelProperty("所在组织id")
    private String organizationId;
    @ApiModelProperty("所在组织path")
    private String organizationPath;

    //C端用户信息
    private boolean includeCustomerInfo;
    private String ownerUrl;
    private String faceUrl;
    private String cardtype;
    private String idcard;
    private String phone;
    private String address;
    private String postcode;
    private String regionId;
    private String nationality;
    private String homepage;
    private String introduce;
    private String wechat;
    private String qq;
    private String sinaWeibo;
    private String alipayNo;
    private String facebookNo;
    private String twitterNo;

    public boolean isIncludeCustomerInfo() {
        return includeCustomerInfo;
    }

    public void setIncludeCustomerInfo(boolean includeCustomerInfo) {
        this.includeCustomerInfo = includeCustomerInfo;
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public void setOwnerUrl(String ownerUrl) {
        this.ownerUrl = ownerUrl;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String getWechat() {
        return wechat;
    }

    @Override
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Override
    public String getQq() {
        return qq;
    }

    @Override
    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSinaWeibo() {
        return sinaWeibo;
    }

    public void setSinaWeibo(String sinaWeibo) {
        this.sinaWeibo = sinaWeibo;
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationPath() {
        return organizationPath;
    }

    public void setOrganizationPath(String organizationPath) {
        this.organizationPath = organizationPath;
    }

    public List<RoleDTO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDTO> roleList) {
        this.roleList = roleList;
    }
}
