package com.sida.dcloud.auth.vo;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by xieguopei on 2017/9/6.
 */
public class EmployeeFilterDTO extends BaseEntity {
    @ApiModelProperty("员工姓名")
    private String name;
    @ApiModelProperty("员工手机号码")
    private String phone;
    @ApiModelProperty("岗位id")
    private String posId;
    @ApiModelProperty("片区 id 用于查询")
    private String areaId;
    @ApiModelProperty("片区名称")
    private String areaName;
    @ApiModelProperty("员工id")
    private String id;
    @ApiModelProperty("组织id")
    private String orgId;
    @ApiModelProperty("查询类型 1。按手机号查询 2。按姓名查询")
    private String queryType;
    @ApiModelProperty("岗位编码")
    private String teachPostionCode;

    public String getTeachPostionCode() {
        return teachPostionCode;
    }

    public void setTeachPostionCode(String teachPostionCode) {
        this.teachPostionCode = teachPostionCode;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }
}
