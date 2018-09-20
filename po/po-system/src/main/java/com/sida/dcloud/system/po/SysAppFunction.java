/**
 * create by Administrator
 * @date 2018-02
 */
package com.sida.dcloud.system.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SysAppFunction extends BaseEntity implements Serializable {
    @ApiModelProperty("")
    private String id;

    @ApiModelProperty("功能名称")
    private String functionName;

    @ApiModelProperty("功能类型（1路由 2.地址）")
    private String functionType;

    @ApiModelProperty("功能编码")
    private String functionCode;

    @ApiModelProperty("连接")
    private String herf;

    @ApiModelProperty("应用id")
    private String appId;

    @ApiModelProperty("资源路径")
    private String sourceUrl;

    @ApiModelProperty("")
    private String icon;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType == null ? null : functionType.trim();
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode == null ? null : functionCode.trim();
    }

    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {
        this.herf = herf == null ? null : herf.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
}