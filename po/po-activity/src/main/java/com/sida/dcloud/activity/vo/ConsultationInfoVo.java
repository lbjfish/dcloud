/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.activity.vo;

import com.sida.xiruo.po.common.BaseEntity;
import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class ConsultationInfoVo extends UserCentricDTO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("企业id（关联operation.company_info的id）")
    private String companyId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("对接开始时间")
    private Date conStartTime;

    @ApiModelProperty("对接结束时间")
    private Date conEndTime;

    @ApiModelProperty("对接地点")
    private String conLocation;

    @ApiModelProperty("对接方式")
    private String conMode;

    @ApiModelProperty("对接安排")
    private String conScheduler;

    private static final long serialVersionUID = 1L;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getConStartTime() {
        return conStartTime;
    }

    public void setConStartTime(Date conStartTime) {
        this.conStartTime = conStartTime;
    }

    public Date getConEndTime() {
        return conEndTime;
    }

    public void setConEndTime(Date conEndTime) {
        this.conEndTime = conEndTime;
    }

    public String getConLocation() {
        return conLocation;
    }

    public void setConLocation(String conLocation) {
        this.conLocation = conLocation;
    }

    public String getConMode() {
        return conMode;
    }

    public void setConMode(String conMode) {
        this.conMode = conMode;
    }

    public String getConScheduler() {
        return conScheduler;
    }

    public void setConScheduler(String conScheduler) {
        this.conScheduler = conScheduler;
    }
}