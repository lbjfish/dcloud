/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.operation.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CompanyInfoVo extends UserCentricDTO {

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

    @ApiModelProperty("所在地区名称")
    private String regionName;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("成立时间")
    private Date foundDate;

    @ApiModelProperty("公司规模（员工数量）")
    private String personTotal;

    @ApiModelProperty("企业图标（七牛云图片地址）")
    private String logoUrl;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getEngShortName() {
        return engShortName;
    }

    public void setEngShortName(String engShortName) {
        this.engShortName = engShortName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        this.personTotal = personTotal;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}