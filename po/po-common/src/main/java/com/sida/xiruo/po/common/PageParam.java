package com.sida.xiruo.po.common;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 通用分页请求参数
 * @author wuzhenwei
 */
public class PageParam implements Serializable{

    @ApiModelProperty(value = "页数(不传或为负则为不分页)", example = "1")
    private Integer p;

    @ApiModelProperty(value = "每页条数", example = "10")
    private Integer s;

    public Integer getP() {
        return (p!=null&&p>0)?p:-1;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getS() {
        return (s!=null&&s>0)?s:10;
    }

    public void setS(Integer s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "PageParam{" +
                "p=" + p +
                ", s=" + s +
                '}';
    }
}
