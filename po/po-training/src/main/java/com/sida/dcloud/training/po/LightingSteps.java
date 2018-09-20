/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class LightingSteps extends BaseEntity {
    @ApiModelProperty("灯光模拟信息id")
    private String lightingSimulatorId;

    @ApiModelProperty("灯光模拟信息")
    private LightingSimulator lightingSimulator;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("排序标志")
    private Integer sort;

    @ApiModelProperty("图片urls")
    private String urls;

    @ApiModelProperty("答案")
    private String answer;

    @ApiModelProperty("备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public LightingSimulator getLightingSimulator() {
        return lightingSimulator;
    }

    public void setLightingSimulator(LightingSimulator lightingSimulator) {
        this.lightingSimulator = lightingSimulator;
    }

    public String getLightingSimulatorId() {
        return lightingSimulatorId;
    }

    public void setLightingSimulatorId(String lightingSimulatorId) {
        this.lightingSimulatorId = lightingSimulatorId == null ? null : lightingSimulatorId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls == null ? null : urls.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}