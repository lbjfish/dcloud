package com.sida.dcloud.auth.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

public class SysUser extends BaseEntity implements Serializable {
    @ApiModelProperty("通用字段：主键id")
    private String id;

    @ApiModelProperty("通用字段：组织id（驾校id）")
    private String orgId;

    @ApiModelProperty("通用字段：创建时间")
    private Date createdAt;

    @ApiModelProperty("通用字段：更新时间（只保留最后一次操作时间）")
    private Date lastUpdated;

    @ApiModelProperty("通用字段：创建人")
    private String createdUser;

    @ApiModelProperty("通用字段：更新人（只保留最后一次操作人）")
    private String updatedUser;

    @ApiModelProperty("通用字段：禁用标识（0.可用  1.禁用）")
    private Boolean disable;

    @ApiModelProperty("通用字段：逻辑删除标识（0.未删除 1.已删除）")
    private Boolean deleteFlag;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

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

    @ApiModelProperty("锁定状态（0.未锁定 1.已锁定）")
    private Boolean locked;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("停用状态（0.启用 1.停用）")
    private Boolean status;

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

    @ApiModelProperty("是否默认创建（0-否 1-是），人事系统导入时创建则为1，手动创建或原为默认创建但手动维护过则为0")
    private String defaultCreated;

    @ApiModelProperty("审核状态：0-未审核，1-审核通过，2-审核拒绝")
    private String approvalStatus;

    @ApiModelProperty("人脸识别标识")
    private String faceId;

    @ApiModelProperty("远程教育用户角色")
    private String remoteRole;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public Boolean getLocked() {
        return Optional.ofNullable(locked).orElse(false);
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getDefaultCreated() {
        return defaultCreated;
    }

    public void setDefaultCreated(String defaultCreated) {
        this.defaultCreated = defaultCreated;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getRemoteRole() {
        return remoteRole;
    }

    public void setRemoteRole(String remoteRole) {
        this.remoteRole = remoteRole;
    }

    public SysUser() {
    }

    public SysUser(String id, String orgId, Date createdAt, Date lastUpdated, String createdUser, String updatedUser, Boolean disable, Boolean deleteFlag, String account, String password, String name, String code, String alias, String email, Date validDate, Boolean locked, String mobile, String description, Boolean status, String nationality, String idType, String idNum, String sex, Date birthday, String tel, String qq, String wechat, String defaultCreated) {
        this.id = id;
        this.orgId = orgId;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.createdUser = createdUser;
        this.updatedUser = updatedUser;
        this.disable = disable;
        this.deleteFlag = deleteFlag;
        this.account = account;
        this.password = password;
        this.name = name;
        this.code = code;
        this.alias = alias;
        this.email = email;
        this.validDate = validDate;
        this.locked = locked;
        this.mobile = mobile;
        this.description = description;
        this.status = status;
        this.nationality = nationality;
        this.idType = idType;
        this.idNum = idNum;
        this.sex = sex;
        this.birthday = birthday;
        this.tel = tel;
        this.qq = qq;
        this.wechat = wechat;
        this.defaultCreated = defaultCreated;
        this.approvalStatus = approvalStatus;
        this.faceId = faceId;
        this.remoteRole = remoteRole;
    }
}