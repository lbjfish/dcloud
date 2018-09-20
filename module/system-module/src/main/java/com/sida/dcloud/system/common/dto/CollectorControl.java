package com.sida.dcloud.system.common.dto;

/**
 * Created by xieguopei on 2017/8/9.
 */
public class CollectorControl {
    private String controlId;
    private String controlName;
    private String controlClass;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getControlId() {
        return controlId;
    }

    public void setControlId(String controlId) {
        this.controlId = controlId;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlClass() {
        return controlClass;
    }

    public void setControlClass(String controlClass) {
        this.controlClass = controlClass;
    }
}
