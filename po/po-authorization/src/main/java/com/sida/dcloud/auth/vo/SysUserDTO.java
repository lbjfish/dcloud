package com.sida.dcloud.auth.vo;

import com.sida.xiruo.po.common.PageParam;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SysUserDTO extends PageParam{

    @ApiModelProperty("必填：账号|手机号-必填")
    private String account;

    @ApiModelProperty("必填：密码")
    private String password;

    @ApiModelProperty("短信验证码")
    private String authCode;

    @ApiModelProperty("选填：远程教育用户角色")
    private String remoteRole;

    @ApiModelProperty("选填：姓名")
    private String name;

    @ApiModelProperty("选填：身份证号")
    private String idNum;

    @ApiModelProperty("选填：性别(0,男，1女)")
    private String sex;

    @ApiModelProperty("用户id集合")
    private String ids;

    @ApiModelProperty("审核操作：0-审核拒绝；1-审核通过")
    private String action;

    @ApiModelProperty("审核状态：0-未审核，1-审核通过，2-审核拒绝")
    private String approvalStatus;

    @ApiModelProperty("人脸识别标识")
    private String faceId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRemoteRole() {
        return remoteRole;
    }

    public void setRemoteRole(String remoteRole) {
        this.remoteRole = remoteRole;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }
}
