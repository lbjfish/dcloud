package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysOrg;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SysOrgVo extends SysOrg{

    @ApiModelProperty("id集合")
    private List<String> idIn;
    @ApiModelProperty("类型集合")
    private List<Integer> typeIn;
    @ApiModelProperty("片区禁用状态")
    private Boolean areaStatus;
    @ApiModelProperty("门店禁用状态")
    private Boolean storeStatus;
    @ApiModelProperty("片区id")
    private String areaId;
    @ApiModelProperty("片区名称")
    private String areaName;

    public List<String> getIdIn() {
        return idIn;
    }

    public void setIdIn(List<String> idIn) {
        this.idIn = idIn;
    }

    public List<Integer> getTypeIn() {
        return typeIn;
    }

    public void setTypeIn(List<Integer> typeIn) {
        this.typeIn = typeIn;
    }

    public Boolean getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(Boolean areaStatus) {
        this.areaStatus = areaStatus;
    }

    public Boolean getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(Boolean storeStatus) {
        this.storeStatus = storeStatus;
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
}
