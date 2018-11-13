package com.sida.dcloud.operation.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TagGroupVo extends UserCentricDTO {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("英文名")
    private String engName;

    @ApiModelProperty("限选数量")
    private Integer limited;

    @ApiModelProperty("允许为空")
    private Boolean allowEmpty;
    @ApiModelProperty("标签列表")
    private List<TagLibVo> tagList;

    public List<TagLibVo> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagLibVo> tagList) {
        this.tagList = tagList;
    }

    public Integer getLimited() {
        return limited;
    }

    public void setLimited(Integer limited) {
        this.limited = limited;
    }

    public Boolean getAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(Boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }
}
