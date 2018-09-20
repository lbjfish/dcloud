/**
 * create by Administrator
 * @date 2018-07
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class IconGroup extends BaseEntity {
    @ApiModelProperty("增长量")
    private Integer increase;

    public Integer getIncrease() {
        return increase;
    }

    public void setIncrease(Integer increase) {
        this.increase = increase;
    }

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("上级分组id")
    private String parentId;

    public String getParentIdPath() {
        return parentIdPath;
    }

    public void setParentIdPath(String parentIdPath) {
        this.parentIdPath = parentIdPath;
    }

    @ApiModelProperty("上级分组id路径")
    private String parentIdPath;

    @ApiModelProperty("id路径")
    private String idPath;

    @ApiModelProperty("本组下图标数量")
    private Integer total;

    @ApiModelProperty("排序标志")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("属于这个分组的图标集合")
    private List<IconInfo> iconInfoList;

    public List<IconInfo> getIconInfoList() {
        return iconInfoList;
    }

    public void setIconInfoList(List<IconInfo> iconInfoList) {
        this.iconInfoList = iconInfoList;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath == null ? null : idPath.trim();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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