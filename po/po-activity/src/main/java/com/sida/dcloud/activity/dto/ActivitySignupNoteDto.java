package com.sida.dcloud.activity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitySignupNoteDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("报名表设置列表")
    private List<ActivitySignupNoteSettingDto> settingList;
    @ApiModelProperty("字段值集合")
    private Map<String, Object> valueMap = new HashMap<>();

    public List<ActivitySignupNoteSettingDto> getSettingList() {
        return settingList;
    }

    public void setSettingList(List<ActivitySignupNoteSettingDto> settingList) {
        this.settingList = settingList;
    }

    public Map<String, Object> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }
}
