/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.operation.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class CompanyExtendItems extends BaseEntity implements Serializable {
    @ApiModelProperty("通用字段：禁用标识（0.可用  1.禁用）")
    private Boolean disable;

    @ApiModelProperty("企业id（关联company_info的id）")
    private String companyId;

    @ApiModelProperty("扩展类型（字典：company_extend_type）")
    private String companyExtendType;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("英文名")
    private String engName;

    @ApiModelProperty("图片（七牛云）")
    private String imageUrl;

    @ApiModelProperty("是否横向（0：否，1：是）")
    private Boolean isHorizontal;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty("排序值（数字小的排前面）")
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyExtendType() {
        return companyExtendType;
    }

    public void setCompanyExtendType(String companyExtendType) {
        this.companyExtendType = companyExtendType == null ? null : companyExtendType.trim();
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

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName == null ? null : engName.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Boolean getIsHorizontal() {
        return isHorizontal;
    }

    public void setIsHorizontal(Boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}