/**
 * FileName：EmployeeAreaDto
 * Author：  chenguanshou
 * Date：    2017/10/17 0017 上午 9:45
 */
package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

public class EmployeeAreaDto {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("员工id")
    private String id;
    @ApiModelProperty("片区id")
    private String areaId;
    @ApiModelProperty("片区名称")
    private String areaName;

    @ApiModelProperty("id路径")
     private String idPath;
    @ApiModelProperty("岗位名称")
    private String positionName;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
