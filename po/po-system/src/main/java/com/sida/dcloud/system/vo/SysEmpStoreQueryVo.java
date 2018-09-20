package com.sida.dcloud.system.vo;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class SysEmpStoreQueryVo extends BaseEntity{
    @ApiModelProperty("岗位编码")
    private String positionCode;
    @ApiModelProperty("门店ID")
    private String storeId;
    @ApiModelProperty("片区ID")
    private String areaId;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @ApiModelProperty("关键词")
    private String query;
    @ApiModelProperty("查询方式：1.客服姓名查询2.按手机号码查询")
    private String queryType;

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
}
