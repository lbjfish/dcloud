/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class HonoredGuest extends BaseEntity implements Serializable {
    @ApiModelProperty("通用字段：禁用标识（0.可用  1.禁用）")
    private Boolean disable;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("英文名")
    private String engName;

    @ApiModelProperty("昵称")
    private String alias;

    @ApiModelProperty("身份证号码")
    private String idNo;

    @ApiModelProperty("移动电话号码")
    private String mobile;

    @ApiModelProperty("所属国家/地区（关联system.sys_region表id）")
    private String nationRegionId;

    @ApiModelProperty("头衔")
    private String honor;

    @ApiModelProperty("排序值（越小越靠前）")
    private Integer sort;

    @ApiModelProperty("用户头像（七牛云图片地址）")
    private String headerUrl;

    @ApiModelProperty("介绍")
    private String introduce;

    @ApiModelProperty("备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName == null ? null : engName.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getNationRegionId() {
        return nationRegionId;
    }

    public void setNationRegionId(String nationRegionId) {
        this.nationRegionId = nationRegionId == null ? null : nationRegionId.trim();
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor == null ? null : honor.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}