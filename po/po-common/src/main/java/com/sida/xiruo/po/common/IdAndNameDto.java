package com.sida.xiruo.po.common;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 */
public class IdAndNameDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("名称")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
