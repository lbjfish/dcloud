package com.sida.xiruo.po.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 通用接口回参格式
 * @param <T>
 * @author wuzhenwei
 */
@ApiModel(description = "通用接口回参格式")
public class ListDTO<T>{

    @ApiModelProperty(value = "列表数据")
    private List<T> list;

    @ApiModelProperty(value = "列表总数")
    private Long total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ListDTO{" +
                "list=" + list +
                ", total=" + total +
                '}';
    }
}
