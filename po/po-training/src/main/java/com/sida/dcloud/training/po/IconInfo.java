/**
 * create by Administrator
 * @date 2018-07
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class IconInfo extends BaseEntity {

    @ApiModelProperty("图标分组id")
    private String groupId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("排序标志")
    private Integer sort;

    @ApiModelProperty("图标URL地址")
    private String url;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty("图标分组引用")
    private IconGroup iconGroup;

    public IconGroup getIconGroup() {
        return iconGroup;
    }

    public void setIconGroup(IconGroup iconGroup) {
        this.iconGroup = iconGroup;
    }

    private static final long serialVersionUID = 1L;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}