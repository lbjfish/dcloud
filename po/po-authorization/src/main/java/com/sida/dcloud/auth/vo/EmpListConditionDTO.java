package com.sida.dcloud.auth.vo;

import com.sida.xiruo.po.common.PageParam;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by xieguopei on 2017/9/5.
 */
public class EmpListConditionDTO extends PageParam{
    @ApiModelProperty("岗位id")
    private String positionId;
    @ApiModelProperty("员工姓名")
    private String name;
    @ApiModelProperty("工号")
    private String workCard;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("在职状态（0正式员工   1试用期  2已离职）")
    private Integer status;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkCard() {
        return workCard;
    }

    public void setWorkCard(String workCard) {
        this.workCard = workCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
