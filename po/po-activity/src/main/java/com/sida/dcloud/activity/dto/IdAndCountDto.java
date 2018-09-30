package com.sida.dcloud.activity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 目前用于从前往后传的底单商品，为了避免被串改价格从后端缓存取
 */
public class IdAndCountDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("数量")
    private Integer count;
    @ApiModelProperty("排序值（值越小越靠前）")
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
