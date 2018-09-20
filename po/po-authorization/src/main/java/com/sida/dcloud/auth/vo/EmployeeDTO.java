package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysEmployee;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by xieguopei on 2017/9/5.
 */
public class EmployeeDTO {

    @ApiModelProperty("员工")
    private SysEmployee sysEmployee;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("员工编码")
    private String empId;

    public SysEmployee getSysEmployee() {
        return sysEmployee;
    }

    public void setSysEmployee(SysEmployee sysEmployee) {
        this.sysEmployee = sysEmployee;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
