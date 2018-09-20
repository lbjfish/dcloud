package com.sida.dcloud.auth.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserInfo {

    //用户信息
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("登录账号")
    private String userAccount;
    @ApiModelProperty("用户名称")
    private String userName;

    //角色信息
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色编码")
    private String roleCode;
    @ApiModelProperty("角色名称")
    private String roleName;

    //多角色拓展
    @ApiModelProperty("角色数组")
    private List<RoleDTO> roleList;

    //组织信息
    @ApiModelProperty("驾校id")
    private String schoolId;
    @ApiModelProperty("所在组织id")
    private String orgId;
    @ApiModelProperty("所在组织path")
    private String orgPath;
    @ApiModelProperty("部门名称")
    private String deptName;
    @ApiModelProperty("所处片区id，没有则为空")
    private String areaId;
    @ApiModelProperty("所处门店id，没有则为空")
    private String storeId;
    @ApiModelProperty("所处牌证部门id，没有则为空")
    private String certId;

    //岗位信息
    @ApiModelProperty("岗位id")
    private String positionId;
    @ApiModelProperty("岗位名称")
    private String positionName;

    //员工信息
    @ApiModelProperty("员工手机")
    private String phone;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("身份证号")
    private String idCardNum;
    @ApiModelProperty("在职状态（0正式员工   1试用期  2已离职）")
    private Integer status;

    //资源信息
    @ApiModelProperty("所拥有的资源列表")
    private List<SysResourceVo> resources;
    @ApiModelProperty("一级菜单目录（前端id集合）")
    private List<String> menus;
    @ApiModelProperty("资源map：key:父级资源id，value:子级资源id")
    private Map<String,List<String>> resMap;
    @ApiModelProperty("用户具有权限的路径跳转集合")
    private List<String> hrefs;

    @ApiModelProperty("用户权限map")
    private LinkedHashMap<String,Object> userResMap;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SysResourceVo> getResources() {
        return resources;
    }

    public void setResources(List<SysResourceVo> resources) {
        this.resources = resources;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public Map<String, List<String>> getResMap() {
        return resMap;
    }

    public void setResMap(Map<String, List<String>> resMap) {
        this.resMap = resMap;
    }

    public List<String> getHrefs() {
        return hrefs;
    }

    public void setHrefs(List<String> hrefs) {
        this.hrefs = hrefs;
    }

    public LinkedHashMap<String, Object> getUserResMap() {
        return userResMap;
    }

    public void setUserResMap(LinkedHashMap<String, Object> userResMap) {
        this.userResMap = userResMap;
    }

    public List<RoleDTO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDTO> roleList) {
        this.roleList = roleList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
