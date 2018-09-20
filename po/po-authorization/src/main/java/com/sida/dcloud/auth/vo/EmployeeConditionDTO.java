package com.sida.dcloud.auth.vo;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by wuzhenwei on 2017/11/24.
 */
public class EmployeeConditionDTO extends BaseEntity{

    @ApiModelProperty("岗位编码：客服专员-ztkfzry;其余待添加")
    private String positionCode;
    @ApiModelProperty("岗位编码数组")
    private List<String> positionCodeGroup;
    @ApiModelProperty("多个岗位编码字符串,逗号分隔")
    private String positionCodeGroupStr;

    @ApiModelProperty("组织id，将组织id由这个字段传入，会将该组织中该岗位的员工返回")
    private String organizationId;
    @ApiModelProperty("组织path，将组织id由这个字段传入，则也会将其下属组织中该岗位的员工返回")
    private String organizationPath;

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public List<String> getPositionCodeGroup() {
        return positionCodeGroup;
    }

    public void setPositionCodeGroup(List<String> positionCodeGroup) {
        this.positionCodeGroup = positionCodeGroup;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationPath() {
        return organizationPath;
    }

    public void setOrganizationPath(String organizationPath) {
        this.organizationPath = organizationPath;
    }

    public String getPositionCodeGroupStr() {
        return positionCodeGroupStr;
    }

    public void setPositionCodeGroupStr(String positionCodeGroupStr) {
        this.positionCodeGroupStr = positionCodeGroupStr;
    }
}
