package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class RegionTreeDTO{

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "等级（“COUNTRY”、“PROVINCE”、“CITY”、“AREA”）")
    private String level;
    @ApiModelProperty(value = "父ID")
    private String parentId;
    @ApiModelProperty(value = "排序值")
    private Integer sort;
    @ApiModelProperty(value = "子节点")
    private List<RegionTreeDTO> children = new ArrayList<>();
    @ApiModelProperty("拼音")
    private String pinyin;

    @ApiModelProperty("拼音首字母")
    private String capitalPinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCapitalPinyin() {
        return capitalPinyin;
    }

    public void setCapitalPinyin(String capitalPinyin) {
        this.capitalPinyin = capitalPinyin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<RegionTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<RegionTreeDTO> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
