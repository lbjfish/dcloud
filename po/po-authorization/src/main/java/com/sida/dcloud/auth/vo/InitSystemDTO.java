package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.*;
import com.sida.dcloud.auth.po.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 初始化系统 请求参数
 */
public class InitSystemDTO {

    @ApiModelProperty("初始化类型：0-全量同步，1-增量同步（全量同步只需传标识，增量同步则需传业务数据集合）")
    private Integer initType;
    @ApiModelProperty("员工业务数据集合")
    private List<SysEmployee> employeeList;
    @ApiModelProperty("组织业务数据集合")
    private List<SysOrg> orgList;
    @ApiModelProperty("岗位业务数据集合")
    private List<SysPosition> positionList;
    @ApiModelProperty("员工-组织关联关系数据集合")
    private List<SysEmployeeOrg> employeeOrgList;
    @ApiModelProperty("员工-岗位关联关系数据集合")
    private List<SysEmployeePosition> employeePositionList;

    public Integer getInitType() {
        return initType;
    }

    public void setInitType(Integer initType) {
        this.initType = initType;
    }

    public List<SysEmployee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<SysEmployee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<SysOrg> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<SysOrg> orgList) {
        this.orgList = orgList;
    }

    public List<SysPosition> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<SysPosition> positionList) {
        this.positionList = positionList;
    }

    public List<SysEmployeeOrg> getEmployeeOrgList() {
        return employeeOrgList;
    }

    public void setEmployeeOrgList(List<SysEmployeeOrg> employeeOrgList) {
        this.employeeOrgList = employeeOrgList;
    }

    public List<SysEmployeePosition> getEmployeePositionList() {
        return employeePositionList;
    }

    public void setEmployeePositionList(List<SysEmployeePosition> employeePositionList) {
        this.employeePositionList = employeePositionList;
    }
}
