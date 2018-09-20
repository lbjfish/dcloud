/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class LightingSimulator extends BaseEntity {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("科目")
    private String subject;

    @ApiModelProperty("排序标志")
    private Integer sort;

    @ApiModelProperty("语音url地址")
    private String audioUrl;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("灯光模拟步骤列表")
    private List<LightingSteps> lightingStepsList = new ArrayList<>();

    public List<LightingSteps> getLightingStepsList() {
        return lightingStepsList;
    }

    public void setLightingStepsList(List<LightingSteps> lightingStepsList) {
        this.lightingStepsList = lightingStepsList;
    }

    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl == null ? null : audioUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}