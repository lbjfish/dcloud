package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserHiddenColumnDTO {

    @ApiModelProperty("必填：页面code（前端控件id）")
    private String pageCode;
    @ApiModelProperty("二选一，隐藏列名称数组")
    private List<String> nameList;
    @ApiModelProperty("二选一，隐藏列code数组")
    private List<String> codeList;

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }
}
