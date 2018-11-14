/**
 * create by user
 * @date 2018-11
 */
package com.sida.dcloud.content.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class SpecialSubjectVo extends UserCentricDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("专题名称")
    private String name;

    @ApiModelProperty("专题滚动图片")
    List<SpecialSubjectImageVo> specialSubjectImageVoList;

    @ApiModelProperty("资讯信息")
    List<InformationVo> informationVoList;


    private static final long serialVersionUID = 1L;

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

    public List<SpecialSubjectImageVo> getSpecialSubjectImageVoList() {
        return specialSubjectImageVoList;
    }

    public void setSpecialSubjectImageVoList(List<SpecialSubjectImageVo> specialSubjectImageVoList) {
        this.specialSubjectImageVoList = specialSubjectImageVoList;
    }

    public List<InformationVo> getInformationVoList() {
        return informationVoList;
    }

    public void setInformationVoList(List<InformationVo> informationVoList) {
        this.informationVoList = informationVoList;
    }
}