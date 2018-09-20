package com.sida.dcloud.system.common.dto;

import com.sida.dcloud.auth.common.SysEnums;

import java.util.List;

/**
 * Created by xieguopei on 2017/8/9.
 */
public class CollectorDirectory {
    private Boolean isDirectory;//是否树节点 true:树菜单，下面还有子菜单   false：叶子菜单，下面没有子菜单
    private String name;
    private String url;//请求的后端接口
    private String code;
    private List<CollectorDirectory> directoryList;
    //按钮类型
    private SysEnums.ResourceTypeEnums type;
    private String href;//前端链接
    private String encode;//md5加密后的href请求

    public Boolean getDirectory() {
        return isDirectory;
    }

    public void setDirectory(Boolean directory) {
        isDirectory = directory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CollectorDirectory> getDirectoryList() {
        return directoryList;
    }

    public void setDirectoryList(List<CollectorDirectory> directoryList) {
        this.directoryList = directoryList;
    }

    public SysEnums.ResourceTypeEnums getType() {
        return type;
    }

    public void setType(SysEnums.ResourceTypeEnums type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }
}
