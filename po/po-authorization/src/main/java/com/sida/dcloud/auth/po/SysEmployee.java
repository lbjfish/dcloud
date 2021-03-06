package com.sida.dcloud.auth.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SysEmployee extends BaseEntity implements Serializable {
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

    @ApiModelProperty("第三方id（原人事系统主键id）")
    private Long thirdPartyId;

    @ApiModelProperty("员工姓名")
    private String name;

    @ApiModelProperty("身份证号码")
    private String idcardNumber;

    @ApiModelProperty("出生年月")
    private Date birthday;

    @ApiModelProperty("性别（0男士 1女士）")
    private String sex;

    @ApiModelProperty("民族")
    private String nation;

    @ApiModelProperty("员工头像（文件路径）")
    private String photo;

    @ApiModelProperty("身份证地址（省）")
    private String idcardAddressProvince;

    @ApiModelProperty("身份证地址（市）")
    private String idcardAddressCity;

    @ApiModelProperty("身份证地址（区）")
    private String idcardAddressArea;

    @ApiModelProperty("身份证地址（详细地址）")
    private String idcardAddressDetail;

    @ApiModelProperty("身份证发证机关")
    private String idcardIissuingAuthority;

    @ApiModelProperty("身份证有效期起")
    private Date idcardValidDate;

    @ApiModelProperty("身份证有效期止")
    private Date idcardInvalidDate;

    @ApiModelProperty("居住证签发日期")
    private Date residencePermitIssuingDate;

    @ApiModelProperty("居住证有效年限（居住证有效期截止日期）")
    private Date residencePermitExpirationDate;

    @ApiModelProperty("身份证有效年限（身份证有效期截止日期）")
    private Date identityCardExpirationDate;

    @ApiModelProperty("居住证地址（省）")
    private String residencePermitAddressProvince;

    @ApiModelProperty("居住证地址（市）")
    private String residencePermitAddressCity;

    @ApiModelProperty("居住证地址（区）")
    private String residencePermitAddressArea;

    @ApiModelProperty("居住证地址（详细地址）")
    private String residencePermitAddressDetail;

    @ApiModelProperty("现住址（省）")
    private String nowAddressProvince;

    @ApiModelProperty("现住址（市）")
    private String nowAddressCity;

    @ApiModelProperty("现住址（区）")
    private String nowAddressArea;

    @ApiModelProperty("现住址（详细地址）")
    private String nowAddressDetail;

    @ApiModelProperty("籍贯（省）")
    private String placeOfOriginProvince;

    @ApiModelProperty("籍贯（市）")
    private String placeOfOriginCity;

    @ApiModelProperty("籍贯（区）")
    private String placeOfOriginArea;

    @ApiModelProperty("出生地")
    private String placeOfBirth;

    @ApiModelProperty("工号")
    private String workCard;

    @ApiModelProperty("必填：手机号码（用于登录账号）")
    private String phone;

    @ApiModelProperty("家庭电话")
    private String mobile;

    @ApiModelProperty("身体状况（健康、一般、残疾）")
    private String healthCondition;

    @ApiModelProperty("特长爱好")
    private String hobby;

    @ApiModelProperty("身高（CM）")
    private String height;

    @ApiModelProperty("血型（A、B、O、AB）")
    private String bloodType;

    @ApiModelProperty("政治面貌（党员、团员、群众、民主党派）")
    private String politicalStatus;

    @ApiModelProperty("毕业时间起")
    private Date graduationStartDate;

    @ApiModelProperty("毕业时间止")
    private Date graduationEndDate;

    @ApiModelProperty("毕业院校")
    private String graduatedSchool;

    @ApiModelProperty("学历")
    private String education;

    @ApiModelProperty("专业")
    private String subject;

    @ApiModelProperty("学历类型（普通统招/成人高考/自考/电大/网络教育）")
    private String academicType;

    @ApiModelProperty("职称（高级/中级/低级）")
    private String jobTitle;

    @ApiModelProperty("职称全称")
    private String titleFullName;

    @ApiModelProperty("参加工作时间")
    private Date startWordDate;

    @ApiModelProperty("报到时间")
    private Date registrationDate;

    @ApiModelProperty("驾驶证档案编号")
    private String driverLicenseNo;

    @ApiModelProperty("驾驶证领取日期")
    private Date driverLicenseReceiveDate;

    @ApiModelProperty("驾驶证有效日期起")
    private Date driverLicenseStartDate;

    @ApiModelProperty("驾驶证有效日期止")
    private Date driverLicenseEndDate;

    @ApiModelProperty("驾驶证有效期（截止日期）")
    private Date driverLicenseExpirationDate;

    @ApiModelProperty("驾驶证发证机关")
    private String driverLicenseIissuingAuthority;

    @ApiModelProperty("教练证领取日期")
    private Date coachCardReceiveDate;

    @ApiModelProperty("教练证有效期（截止日期）")
    private Date coachCardExpirationDate;

    @ApiModelProperty("教练证发证机关")
    private String coachCardIissuingAuthority;

    @ApiModelProperty("准驾车型（C1，C2，C3，C4，D，E，N，M，P）")
    private String quasiDrivingType;

    @ApiModelProperty("教练证号")
    private String coachCardNo;

    @ApiModelProperty("准教类别（C1，C2）")
    private String coachCardQuasiTeachingCategory;

    @ApiModelProperty("准教车型（A3，B1，B2，C1，C2，C3，M）")
    private String quasiTeachingType;

    @ApiModelProperty("风险押金")
    private BigDecimal riskDeposit;

    @ApiModelProperty("风险押金日期")
    private Date riskDepositExpirationDate;

    @ApiModelProperty("紧急联系人")
    private String emergencyContact;

    @ApiModelProperty("与本人关系")
    private String relationship;

    @ApiModelProperty("联系人电话")
    private String contactPhone;

    @ApiModelProperty("婚否（0未婚  1已婚）")
    private Integer maritalStatus;

    @ApiModelProperty("结婚登记日期")
    private Date marriageRegistrationDate;

    @ApiModelProperty("配偶姓名")
    private String spouseName;

    @ApiModelProperty("配偶身份证")
    private String spouseIdentityCard;

    @ApiModelProperty("工作单位")
    private String spouseWorkCompany;

    @ApiModelProperty("配偶职务")
    private String spouseDuties;

    @ApiModelProperty("配偶学历")
    private String spouseEducation;

    @ApiModelProperty("配偶手机号码")
    private String spousePhone;

    @ApiModelProperty("在职状态（0正式员工   1试用期  2已离职）")
    private Integer status;
    @ApiModelProperty("准教车型 冗余字段")
    private String quasiTeachingTypeBak;
    @ApiModelProperty("准教类别 冗余字段")
    private String coachCardQuasiTeachingCategoryBak;
    @ApiModelProperty("准驾车型 冗余字段")
    private String quasiDrivingTypeBak;
    @ApiModelProperty("供职方式 0.直营 1.加盟 2.挂靠")
    private Integer serviceMode;
    @ApiModelProperty("员工类型 1.教练 2.其他")
    private String empType;

    @ApiModelProperty("片区ID")
    private String areaId;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    private String idPath;

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }

    public Integer getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(Integer serviceMode) {
        this.serviceMode = serviceMode;
    }

    public String getQuasiTeachingTypeBak() {
        return quasiTeachingTypeBak;
    }

    public void setQuasiTeachingTypeBak(String quasiTeachingTypeBak) {
        this.quasiTeachingTypeBak = quasiTeachingTypeBak;
    }

    public String getCoachCardQuasiTeachingCategoryBak() {
        return coachCardQuasiTeachingCategoryBak;
    }

    public void setCoachCardQuasiTeachingCategoryBak(String coachCardQuasiTeachingCategoryBak) {
        this.coachCardQuasiTeachingCategoryBak = coachCardQuasiTeachingCategoryBak;
    }

    public String getQuasiDrivingTypeBak() {
        return quasiDrivingTypeBak;
    }

    public void setQuasiDrivingTypeBak(String quasiDrivingTypeBak) {
        this.quasiDrivingTypeBak = quasiDrivingTypeBak;
    }

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

    public Long getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber == null ? null : idcardNumber.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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
        this.nation = nation == null ? null : nation.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public String getIdcardAddressProvince() {
        return idcardAddressProvince;
    }

    public void setIdcardAddressProvince(String idcardAddressProvince) {
        this.idcardAddressProvince = idcardAddressProvince == null ? null : idcardAddressProvince.trim();
    }

    public String getIdcardAddressCity() {
        return idcardAddressCity;
    }

    public void setIdcardAddressCity(String idcardAddressCity) {
        this.idcardAddressCity = idcardAddressCity == null ? null : idcardAddressCity.trim();
    }

    public String getIdcardAddressArea() {
        return idcardAddressArea;
    }

    public void setIdcardAddressArea(String idcardAddressArea) {
        this.idcardAddressArea = idcardAddressArea == null ? null : idcardAddressArea.trim();
    }

    public String getIdcardAddressDetail() {
        return idcardAddressDetail;
    }

    public void setIdcardAddressDetail(String idcardAddressDetail) {
        this.idcardAddressDetail = idcardAddressDetail == null ? null : idcardAddressDetail.trim();
    }

    public String getIdcardIissuingAuthority() {
        return idcardIissuingAuthority;
    }

    public void setIdcardIissuingAuthority(String idcardIissuingAuthority) {
        this.idcardIissuingAuthority = idcardIissuingAuthority == null ? null : idcardIissuingAuthority.trim();
    }

    public Date getIdcardValidDate() {
        return idcardValidDate;
    }

    public void setIdcardValidDate(Date idcardValidDate) {
        this.idcardValidDate = idcardValidDate;
    }

    public Date getIdcardInvalidDate() {
        return idcardInvalidDate;
    }

    public void setIdcardInvalidDate(Date idcardInvalidDate) {
        this.idcardInvalidDate = idcardInvalidDate;
    }

    public Date getResidencePermitIssuingDate() {
        return residencePermitIssuingDate;
    }

    public void setResidencePermitIssuingDate(Date residencePermitIssuingDate) {
        this.residencePermitIssuingDate = residencePermitIssuingDate;
    }

    public Date getResidencePermitExpirationDate() {
        return residencePermitExpirationDate;
    }

    public void setResidencePermitExpirationDate(Date residencePermitExpirationDate) {
        this.residencePermitExpirationDate = residencePermitExpirationDate;
    }

    public Date getIdentityCardExpirationDate() {
        return identityCardExpirationDate;
    }

    public void setIdentityCardExpirationDate(Date identityCardExpirationDate) {
        this.identityCardExpirationDate = identityCardExpirationDate;
    }

    public String getResidencePermitAddressProvince() {
        return residencePermitAddressProvince;
    }

    public void setResidencePermitAddressProvince(String residencePermitAddressProvince) {
        this.residencePermitAddressProvince = residencePermitAddressProvince == null ? null : residencePermitAddressProvince.trim();
    }

    public String getResidencePermitAddressCity() {
        return residencePermitAddressCity;
    }

    public void setResidencePermitAddressCity(String residencePermitAddressCity) {
        this.residencePermitAddressCity = residencePermitAddressCity == null ? null : residencePermitAddressCity.trim();
    }

    public String getResidencePermitAddressArea() {
        return residencePermitAddressArea;
    }

    public void setResidencePermitAddressArea(String residencePermitAddressArea) {
        this.residencePermitAddressArea = residencePermitAddressArea == null ? null : residencePermitAddressArea.trim();
    }

    public String getResidencePermitAddressDetail() {
        return residencePermitAddressDetail;
    }

    public void setResidencePermitAddressDetail(String residencePermitAddressDetail) {
        this.residencePermitAddressDetail = residencePermitAddressDetail == null ? null : residencePermitAddressDetail.trim();
    }

    public String getNowAddressProvince() {
        return nowAddressProvince;
    }

    public void setNowAddressProvince(String nowAddressProvince) {
        this.nowAddressProvince = nowAddressProvince == null ? null : nowAddressProvince.trim();
    }

    public String getNowAddressCity() {
        return nowAddressCity;
    }

    public void setNowAddressCity(String nowAddressCity) {
        this.nowAddressCity = nowAddressCity == null ? null : nowAddressCity.trim();
    }

    public String getNowAddressArea() {
        return nowAddressArea;
    }

    public void setNowAddressArea(String nowAddressArea) {
        this.nowAddressArea = nowAddressArea == null ? null : nowAddressArea.trim();
    }

    public String getNowAddressDetail() {
        return nowAddressDetail;
    }

    public void setNowAddressDetail(String nowAddressDetail) {
        this.nowAddressDetail = nowAddressDetail == null ? null : nowAddressDetail.trim();
    }

    public String getPlaceOfOriginProvince() {
        return placeOfOriginProvince;
    }

    public void setPlaceOfOriginProvince(String placeOfOriginProvince) {
        this.placeOfOriginProvince = placeOfOriginProvince == null ? null : placeOfOriginProvince.trim();
    }

    public String getPlaceOfOriginCity() {
        return placeOfOriginCity;
    }

    public void setPlaceOfOriginCity(String placeOfOriginCity) {
        this.placeOfOriginCity = placeOfOriginCity == null ? null : placeOfOriginCity.trim();
    }

    public String getPlaceOfOriginArea() {
        return placeOfOriginArea;
    }

    public void setPlaceOfOriginArea(String placeOfOriginArea) {
        this.placeOfOriginArea = placeOfOriginArea == null ? null : placeOfOriginArea.trim();
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth == null ? null : placeOfBirth.trim();
    }

    public String getWorkCard() {
        return workCard;
    }

    public void setWorkCard(String workCard) {
        this.workCard = workCard == null ? null : workCard.trim();
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

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition == null ? null : healthCondition.trim();
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType == null ? null : bloodType.trim();
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus == null ? null : politicalStatus.trim();
    }

    public Date getGraduationStartDate() {
        return graduationStartDate;
    }

    public void setGraduationStartDate(Date graduationStartDate) {
        this.graduationStartDate = graduationStartDate;
    }

    public Date getGraduationEndDate() {
        return graduationEndDate;
    }

    public void setGraduationEndDate(Date graduationEndDate) {
        this.graduationEndDate = graduationEndDate;
    }

    public String getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(String graduatedSchool) {
        this.graduatedSchool = graduatedSchool == null ? null : graduatedSchool.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getAcademicType() {
        return academicType;
    }

    public void setAcademicType(String academicType) {
        this.academicType = academicType == null ? null : academicType.trim();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle == null ? null : jobTitle.trim();
    }

    public String getTitleFullName() {
        return titleFullName;
    }

    public void setTitleFullName(String titleFullName) {
        this.titleFullName = titleFullName == null ? null : titleFullName.trim();
    }

    public Date getStartWordDate() {
        return startWordDate;
    }

    public void setStartWordDate(Date startWordDate) {
        this.startWordDate = startWordDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDriverLicenseNo() {
        return driverLicenseNo;
    }

    public void setDriverLicenseNo(String driverLicenseNo) {
        this.driverLicenseNo = driverLicenseNo == null ? null : driverLicenseNo.trim();
    }

    public Date getDriverLicenseReceiveDate() {
        return driverLicenseReceiveDate;
    }

    public void setDriverLicenseReceiveDate(Date driverLicenseReceiveDate) {
        this.driverLicenseReceiveDate = driverLicenseReceiveDate;
    }

    public Date getDriverLicenseStartDate() {
        return driverLicenseStartDate;
    }

    public void setDriverLicenseStartDate(Date driverLicenseStartDate) {
        this.driverLicenseStartDate = driverLicenseStartDate;
    }

    public Date getDriverLicenseEndDate() {
        return driverLicenseEndDate;
    }

    public void setDriverLicenseEndDate(Date driverLicenseEndDate) {
        this.driverLicenseEndDate = driverLicenseEndDate;
    }

    public Date getDriverLicenseExpirationDate() {
        return driverLicenseExpirationDate;
    }

    public void setDriverLicenseExpirationDate(Date driverLicenseExpirationDate) {
        this.driverLicenseExpirationDate = driverLicenseExpirationDate;
    }

    public String getDriverLicenseIissuingAuthority() {
        return driverLicenseIissuingAuthority;
    }

    public void setDriverLicenseIissuingAuthority(String driverLicenseIissuingAuthority) {
        this.driverLicenseIissuingAuthority = driverLicenseIissuingAuthority == null ? null : driverLicenseIissuingAuthority.trim();
    }

    public Date getCoachCardReceiveDate() {
        return coachCardReceiveDate;
    }

    public void setCoachCardReceiveDate(Date coachCardReceiveDate) {
        this.coachCardReceiveDate = coachCardReceiveDate;
    }

    public Date getCoachCardExpirationDate() {
        return coachCardExpirationDate;
    }

    public void setCoachCardExpirationDate(Date coachCardExpirationDate) {
        this.coachCardExpirationDate = coachCardExpirationDate;
    }

    public String getCoachCardIissuingAuthority() {
        return coachCardIissuingAuthority;
    }

    public void setCoachCardIissuingAuthority(String coachCardIissuingAuthority) {
        this.coachCardIissuingAuthority = coachCardIissuingAuthority == null ? null : coachCardIissuingAuthority.trim();
    }

    public String getQuasiDrivingType() {
        return quasiDrivingType;
    }

    public void setQuasiDrivingType(String quasiDrivingType) {
        this.quasiDrivingType = quasiDrivingType == null ? null : quasiDrivingType.trim();
    }

    public String getCoachCardNo() {
        return coachCardNo;
    }

    public void setCoachCardNo(String coachCardNo) {
        this.coachCardNo = coachCardNo == null ? null : coachCardNo.trim();
    }

    public String getCoachCardQuasiTeachingCategory() {
        return coachCardQuasiTeachingCategory;
    }

    public void setCoachCardQuasiTeachingCategory(String coachCardQuasiTeachingCategory) {
        this.coachCardQuasiTeachingCategory = coachCardQuasiTeachingCategory == null ? null : coachCardQuasiTeachingCategory.trim();
    }

    public String getQuasiTeachingType() {
        return quasiTeachingType;
    }

    public void setQuasiTeachingType(String quasiTeachingType) {
        this.quasiTeachingType = quasiTeachingType == null ? null : quasiTeachingType.trim();
    }

    public BigDecimal getRiskDeposit() {
        return riskDeposit;
    }

    public void setRiskDeposit(BigDecimal riskDeposit) {
        this.riskDeposit = riskDeposit;
    }

    public Date getRiskDepositExpirationDate() {
        return riskDepositExpirationDate;
    }

    public void setRiskDepositExpirationDate(Date riskDepositExpirationDate) {
        this.riskDepositExpirationDate = riskDepositExpirationDate;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact == null ? null : emergencyContact.trim();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Date getMarriageRegistrationDate() {
        return marriageRegistrationDate;
    }

    public void setMarriageRegistrationDate(Date marriageRegistrationDate) {
        this.marriageRegistrationDate = marriageRegistrationDate;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName == null ? null : spouseName.trim();
    }

    public String getSpouseIdentityCard() {
        return spouseIdentityCard;
    }

    public void setSpouseIdentityCard(String spouseIdentityCard) {
        this.spouseIdentityCard = spouseIdentityCard == null ? null : spouseIdentityCard.trim();
    }

    public String getSpouseWorkCompany() {
        return spouseWorkCompany;
    }

    public void setSpouseWorkCompany(String spouseWorkCompany) {
        this.spouseWorkCompany = spouseWorkCompany == null ? null : spouseWorkCompany.trim();
    }

    public String getSpouseDuties() {
        return spouseDuties;
    }

    public void setSpouseDuties(String spouseDuties) {
        this.spouseDuties = spouseDuties == null ? null : spouseDuties.trim();
    }

    public String getSpouseEducation() {
        return spouseEducation;
    }

    public void setSpouseEducation(String spouseEducation) {
        this.spouseEducation = spouseEducation == null ? null : spouseEducation.trim();
    }

    public String getSpousePhone() {
        return spousePhone;
    }

    public void setSpousePhone(String spousePhone) {
        this.spousePhone = spousePhone == null ? null : spousePhone.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}