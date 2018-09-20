package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 门店DTO
 */
public class StoreDTO {
    @ApiModelProperty("门店id")
    private String storeId;
    @ApiModelProperty("门店名称")
    private String storeName;
    @ApiModelProperty("门店所在组织表idPath")
    private String orgPath;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }
}
