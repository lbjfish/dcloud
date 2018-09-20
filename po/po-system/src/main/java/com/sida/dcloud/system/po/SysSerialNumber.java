/**
 * create by huangbaidong
 * @date 2017-03
 */
package com.sida.dcloud.system.po;

import java.io.Serializable;

public class SysSerialNumber extends SysSerialNumberKey implements Serializable {
    private Long number;

    private String datestr;

    private String description;

    private static final long serialVersionUID = 1L;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDatestr() {
        return datestr;
    }

    public void setDatestr(String datestr) {
        this.datestr = datestr == null ? null : datestr.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}