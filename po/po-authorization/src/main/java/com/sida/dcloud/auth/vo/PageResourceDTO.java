package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 页面资源dto
 */
public class PageResourceDTO {

    @ApiModelProperty("按钮权限列表")
    private List<ResourceItemDTO> buttonList;
    @ApiModelProperty("字段权限列表")
    private List<ResourceItemDTO> fieldList;
    @ApiModelProperty("用户具有的按钮权限")
    private List<ResourceItemDTO> userButton;
    @ApiModelProperty("用户具有的字段权限")
    private List<ResourceItemDTO> userField;

    @ApiModelProperty("按钮权限名称数组")
    private List<String> userFieldNames;
    @ApiModelProperty("字段权限名称数组")
    private Map<String,Boolean> userButtonCodes;

    public List<ResourceItemDTO> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<ResourceItemDTO> buttonList) {
        this.buttonList = buttonList;
    }

    public List<ResourceItemDTO> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ResourceItemDTO> fieldList) {
        this.fieldList = fieldList;
    }

    public List<ResourceItemDTO> getUserButton() {
        return userButton;
    }

    public void setUserButton(List<ResourceItemDTO> userButton) {
        this.userButton = userButton;
    }

    public List<ResourceItemDTO> getUserField() {
        return userField;
    }

    public void setUserField(List<ResourceItemDTO> userField) {
        this.userField = userField;
    }

    public List<String> getUserFieldNames() {
        return userFieldNames;
    }

    public void setUserFieldNames(List<String> userFieldNames) {
        this.userFieldNames = userFieldNames;
    }

    public Map<String, Boolean> getUserButtonCodes() {
        return userButtonCodes;
    }

    public void setUserButtonCodes(Map<String, Boolean> userButtonCodes) {
        this.userButtonCodes = userButtonCodes;
    }
}
