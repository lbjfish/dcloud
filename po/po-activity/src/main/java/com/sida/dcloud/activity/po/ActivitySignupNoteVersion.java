/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class ActivitySignupNoteVersion extends BaseEntity implements Serializable {
    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("版本时间")
    private Date versionTime;

    @ApiModelProperty("是否当前使用（0：否，1：是）")
    private Boolean currentUsed;

    private static final long serialVersionUID = 1L;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Date getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(Date versionTime) {
        this.versionTime = versionTime;
    }

    public Boolean getCurrentUsed() {
        return currentUsed;
    }

    public void setCurrentUsed(Boolean currentUsed) {
        this.currentUsed = currentUsed;
    }
}