/**
 * create by user
 * @date 2018-11
 */
package com.sida.dcloud.content.vo;

import com.sida.xiruo.po.common.BaseEntity;
import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SpecialSubjectImageVo extends UserCentricDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("图片")
    private String image;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}