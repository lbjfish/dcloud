package com.sida.dcloud.auth.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysOrg extends BaseEntity implements Serializable {
    @ApiModelProperty("通用字段：主键id")
    private String id;

    @ApiModelProperty("通用字段：组织id（驾校id）")
    private String orgId;

    @ApiModelProperty("通用字段：创建时间")
    private Date createdAt;

    @ApiModelProperty("通用字段：更新时间（只保留最后一次操作时间）")
    private Date lastUpdated;

    @ApiModelProperty("通用字段：创建人")
    private String createdUser;

    @ApiModelProperty("通用字段：更新人（只保留最后一次操作人）")
    private String updatedUser;

    @ApiModelProperty("通用字段：禁用标识（0.可用  1.禁用）")
    private Boolean disable;

    @ApiModelProperty("通用字段：逻辑删除标识（0.未删除 1.已删除）")
    private Boolean deleteFlag;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("别名")
    private String alias;

    @ApiModelProperty("父级id")
    private String parentId;

    @ApiModelProperty("id路径")
    private String idPath;

    @ApiModelProperty("名称路径")
    private String namePath;

    @ApiModelProperty("编码路径")
    private String codePath;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("公司logo")
    private String logo;

    @ApiModelProperty("是否具备人事管理系统（员工来源是否是从原系统同步）")
    private Boolean hasPersonnelSystem;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("特殊部门标识（0.非特殊部门  1.牌证部门  2.片区  3.门店 4.公司 5.职能部门 6.场点 7.商圈 8.大区）")
    private Integer type;
    @ApiModelProperty("第三方id 原人事系统id")
    private Long thirdPartyId;
    @ApiModelProperty("是否驾校")
    private Boolean isSchool;
    @ApiModelProperty("来源部门标识")
    private Integer sourceType;
    @ApiModelProperty("对应业务扩展表状态")
    private String businessStatus;
    @ApiModelProperty("门店代码")
    private String storeCode;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Long getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    @ApiModelProperty(value = "子节点")
    private List<SysOrg> children = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath == null ? null : idPath.trim();
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath == null ? null : namePath.trim();
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath == null ? null : codePath.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public Boolean getHasPersonnelSystem() {
        return hasPersonnelSystem;
    }

    public void setHasPersonnelSystem(Boolean hasPersonnelSystem) {
        this.hasPersonnelSystem = hasPersonnelSystem;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<SysOrg> getChildren() {
        return children;
    }

    public void setChildren(List<SysOrg> children) {
        this.children = children;
    }

    public Boolean getSchool() {
        return isSchool;
    }

    public void setSchool(Boolean school) {
        isSchool = school;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }
}