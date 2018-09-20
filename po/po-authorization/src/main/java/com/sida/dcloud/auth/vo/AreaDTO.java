package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 片区DTO
 */
public class AreaDTO {
    @ApiModelProperty("片区id")
    private String areaId;
    @ApiModelProperty("片区名称")
    private String areaName;
    @ApiModelProperty("片区所在组织表idPath")
    private String orgPath;
    @ApiModelProperty("下属门店")
    private List<StoreDTO> children;
    @ApiModelProperty("下属门店-联动使用")
    private List<AreaDTO> childrenStore;

    public List<AreaDTO> getChildrenStore() {
        return childrenStore;
    }

    public void setChildrenStore(List<AreaDTO> childrenStore) {
        this.childrenStore = childrenStore;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public List<StoreDTO> getChildren() {
        return children;
    }

    public void setChildren(List<StoreDTO> children) {
        this.children = children;
    }
}
