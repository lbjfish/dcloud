package com.sida.dcloud.system.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SysRegionSingleLayerDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("通用字段：主键id")
    private String id;

    @ApiModelProperty("地区编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("邮政编码")
    private String postCode;

    @ApiModelProperty("级别")
    private String level;

    @ApiModelProperty("拼音")
    private String pinyin;

    @ApiModelProperty("拼音首字母")
    private String capitalPinyin;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

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
}
