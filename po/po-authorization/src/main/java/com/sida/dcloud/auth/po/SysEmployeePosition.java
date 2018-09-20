/**
 * create by Administrator
 * @date 2017-08
 */
package com.sida.dcloud.auth.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SysEmployeePosition extends BaseEntity implements Serializable {
    @ApiModelProperty("员工id")
    private String employeeId;

    @ApiModelProperty("岗位id")
    private String positionId;

    private static final long serialVersionUID = 1L;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
    }
}