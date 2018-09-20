/**
 * create by Administrator
 * @date 2017-08
 */
package com.sida.dcloud.auth.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SysEmployeeOrg extends BaseEntity implements Serializable {
    @ApiModelProperty("员工")
    private String employeeId;

    @ApiModelProperty("组织id")
    private String orgId;

    private static final long serialVersionUID = 1L;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}