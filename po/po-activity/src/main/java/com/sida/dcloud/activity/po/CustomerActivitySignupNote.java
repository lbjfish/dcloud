/**
 * create by jianglingfeng
 * @date 2018-10
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class CustomerActivitySignupNote extends BaseEntity implements Serializable {
    @ApiModelProperty("报名表编号（由规则产生）")
    private String noteNo;

    @ApiModelProperty("第三方识别码")
    private String thirdPartCode;

    @ApiModelProperty("活动id（关联activity_info表id）")
    private String activityId;

    @ApiModelProperty("用户id（关联sys_user_activity表id）")
    private String userId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("部门")
    private String department;

    @ApiModelProperty("职务")
    private String position;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("邮编")
    private String postCode;

    @ApiModelProperty("手机1国家码")
    private String mobile1CountryNo;

    @ApiModelProperty("手机1号码")
    private String mobile1No;

    @ApiModelProperty("手机2国家码")
    private String mobile2CountryNo;

    @ApiModelProperty("手机2号码")
    private String mobile2No;

    @ApiModelProperty("电话1国家码")
    private String phone1CountryNo;

    @ApiModelProperty("电话1区号")
    private String phoneZoneNo;

    @ApiModelProperty("电话1号码")
    private String phone1No;

    @ApiModelProperty("电话2国家码")
    private String phone2CountryNo;

    @ApiModelProperty("电话2区号")
    private String phone2ZoneNo;

    @ApiModelProperty("电话2号码")
    private String phone2No;

    @ApiModelProperty("传真国家码")
    private String faxCountryNo;

    @ApiModelProperty("传真区号")
    private String faxZoneNo;

    @ApiModelProperty("传真号码")
    private String faxNo;

    @ApiModelProperty("电子邮件地址")
    private String email;

    @ApiModelProperty("网址")
    private String homepage;

    @ApiModelProperty("QQ")
    private String qqNo;

    @ApiModelProperty("微信号")
    private String wxNo;

    @ApiModelProperty("所在地区（关联system.sys_region表id）")
    private String regionId;

    @ApiModelProperty("公司所属领域，多选（字典trade_domain）")
    private String corpDomains;

    @ApiModelProperty("感兴趣的领域，多选（字典trade_domain）")
    private String interestedDomains;

    @ApiModelProperty("参加活动的目的")
    private String attendIntent;

    @ApiModelProperty("获取渠道（字典gain_channel）")
    private String gainChannel;

    @ApiModelProperty("证件类型（字典id_type）")
    private String idType;

    @ApiModelProperty("证件号码")
    private String idNo;

    @ApiModelProperty("填报时间")
    private Date signupTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("版本（YYYYMMDD）")
    private String version;

    @ApiModelProperty("扩展字段1")
    private String field1;

    @ApiModelProperty("扩展字段2")
    private String field2;

    @ApiModelProperty("扩展字段3")
    private String field3;

    @ApiModelProperty("扩展字段4")
    private String field4;

    @ApiModelProperty("扩展字段5")
    private String field5;

    @ApiModelProperty("扩展字段6")
    private String field6;

    @ApiModelProperty("扩展字段7")
    private String field7;

    @ApiModelProperty("扩展字段8")
    private String field8;

    @ApiModelProperty("扩展字段9")
    private String field9;

    @ApiModelProperty("扩展字段10")
    private String field10;

    private static final long serialVersionUID = 1L;

    public String getNoteNo() {
        return noteNo;
    }

    public void setNoteNo(String noteNo) {
        this.noteNo = noteNo == null ? null : noteNo.trim();
    }

    public String getThirdPartCode() {
        return thirdPartCode;
    }

    public void setThirdPartCode(String thirdPartCode) {
        this.thirdPartCode = thirdPartCode == null ? null : thirdPartCode.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getMobile1CountryNo() {
        return mobile1CountryNo;
    }

    public void setMobile1CountryNo(String mobile1CountryNo) {
        this.mobile1CountryNo = mobile1CountryNo == null ? null : mobile1CountryNo.trim();
    }

    public String getMobile1No() {
        return mobile1No;
    }

    public void setMobile1No(String mobile1No) {
        this.mobile1No = mobile1No == null ? null : mobile1No.trim();
    }

    public String getMobile2CountryNo() {
        return mobile2CountryNo;
    }

    public void setMobile2CountryNo(String mobile2CountryNo) {
        this.mobile2CountryNo = mobile2CountryNo == null ? null : mobile2CountryNo.trim();
    }

    public String getMobile2No() {
        return mobile2No;
    }

    public void setMobile2No(String mobile2No) {
        this.mobile2No = mobile2No == null ? null : mobile2No.trim();
    }

    public String getPhone1CountryNo() {
        return phone1CountryNo;
    }

    public void setPhone1CountryNo(String phone1CountryNo) {
        this.phone1CountryNo = phone1CountryNo == null ? null : phone1CountryNo.trim();
    }

    public String getPhoneZoneNo() {
        return phoneZoneNo;
    }

    public void setPhoneZoneNo(String phoneZoneNo) {
        this.phoneZoneNo = phoneZoneNo == null ? null : phoneZoneNo.trim();
    }

    public String getPhone1No() {
        return phone1No;
    }

    public void setPhone1No(String phone1No) {
        this.phone1No = phone1No == null ? null : phone1No.trim();
    }

    public String getPhone2CountryNo() {
        return phone2CountryNo;
    }

    public void setPhone2CountryNo(String phone2CountryNo) {
        this.phone2CountryNo = phone2CountryNo == null ? null : phone2CountryNo.trim();
    }

    public String getPhone2ZoneNo() {
        return phone2ZoneNo;
    }

    public void setPhone2ZoneNo(String phone2ZoneNo) {
        this.phone2ZoneNo = phone2ZoneNo == null ? null : phone2ZoneNo.trim();
    }

    public String getPhone2No() {
        return phone2No;
    }

    public void setPhone2No(String phone2No) {
        this.phone2No = phone2No == null ? null : phone2No.trim();
    }

    public String getFaxCountryNo() {
        return faxCountryNo;
    }

    public void setFaxCountryNo(String faxCountryNo) {
        this.faxCountryNo = faxCountryNo == null ? null : faxCountryNo.trim();
    }

    public String getFaxZoneNo() {
        return faxZoneNo;
    }

    public void setFaxZoneNo(String faxZoneNo) {
        this.faxZoneNo = faxZoneNo == null ? null : faxZoneNo.trim();
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo == null ? null : faxNo.trim();
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

    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo == null ? null : qqNo.trim();
    }

    public String getWxNo() {
        return wxNo;
    }

    public void setWxNo(String wxNo) {
        this.wxNo = wxNo == null ? null : wxNo.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getCorpDomains() {
        return corpDomains;
    }

    public void setCorpDomains(String corpDomains) {
        this.corpDomains = corpDomains == null ? null : corpDomains.trim();
    }

    public String getInterestedDomains() {
        return interestedDomains;
    }

    public void setInterestedDomains(String interestedDomains) {
        this.interestedDomains = interestedDomains == null ? null : interestedDomains.trim();
    }

    public String getAttendIntent() {
        return attendIntent;
    }

    public void setAttendIntent(String attendIntent) {
        this.attendIntent = attendIntent == null ? null : attendIntent.trim();
    }

    public String getGainChannel() {
        return gainChannel;
    }

    public void setGainChannel(String gainChannel) {
        this.gainChannel = gainChannel == null ? null : gainChannel.trim();
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public Date getSignupTime() {
        return signupTime;
    }

    public void setSignupTime(Date signupTime) {
        this.signupTime = signupTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4 == null ? null : field4.trim();
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5 == null ? null : field5.trim();
    }

    public String getField6() {
        return field6;
    }

    public void setField6(String field6) {
        this.field6 = field6 == null ? null : field6.trim();
    }

    public String getField7() {
        return field7;
    }

    public void setField7(String field7) {
        this.field7 = field7 == null ? null : field7.trim();
    }

    public String getField8() {
        return field8;
    }

    public void setField8(String field8) {
        this.field8 = field8 == null ? null : field8.trim();
    }

    public String getField9() {
        return field9;
    }

    public void setField9(String field9) {
        this.field9 = field9 == null ? null : field9.trim();
    }

    public String getField10() {
        return field10;
    }

    public void setField10(String field10) {
        this.field10 = field10 == null ? null : field10.trim();
    }
}