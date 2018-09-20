/**
 * create by huangbaidong
 * @date 2017-03
 */
package com.sida.dcloud.system.po;

import com.sida.xiruo.po.common.BaseEntity;

import java.io.Serializable;

public class SysSerialNumberKey extends BaseEntity implements Serializable {
    private String tableKey;

    private String orgId;

    private String periodDateFormat;

    private static final long serialVersionUID = 1L;

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey == null ? null : tableKey.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPeriodDateFormat() {
        return periodDateFormat;
    }

    public void setPeriodDateFormat(String periodDateFormat) {
        this.periodDateFormat = periodDateFormat == null ? null : periodDateFormat.trim();
    }
}