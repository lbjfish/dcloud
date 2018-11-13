/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.operation.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class CompanyImages extends BaseEntity implements Serializable {
    @ApiModelProperty("通用字段：禁用标识（0.可用  1.禁用）")
    private Boolean disable;

    @ApiModelProperty("用户id（关联company_info的id）")
    private String companyId;

    @ApiModelProperty("图片类型（字典：company_image_type）")
    private String companyImageType;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("图片（七牛云）")
    private String imageUrl;

    @ApiModelProperty("排序值（数字小的排前面）")
    private Integer sort;

    @ApiModelProperty("描述")
    private String remark;

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

    public String getCompanyImageType() {
        return companyImageType;
    }

    public void setCompanyImageType(String companyImageType) {
        this.companyImageType = companyImageType == null ? null : companyImageType.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}