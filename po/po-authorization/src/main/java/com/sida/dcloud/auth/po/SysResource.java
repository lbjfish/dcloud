package com.sida.dcloud.auth.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class SysResource extends BaseEntity implements Serializable {
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

    @ApiModelProperty("标识字段")
    private String href;

    @ApiModelProperty("标识字段")
    private String value;

    @ApiModelProperty("父级id")
    private String parentId;

    @ApiModelProperty("id路径（用.分隔）")
    private String path;

    @ApiModelProperty("排序值（数字小的排前面）")
    private Integer sort;

    @ApiModelProperty("备注")
    private String description;

    @ApiModelProperty("别名")
    private String alias;

    @ApiModelProperty("类别（“page”，“button”，“field”）")
    private String type;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public SysResource() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public SysResource(String name, String code, String href, String parentId, Integer sort, String value, String type) {
        this.name = name;
        this.code = code;
        this.href = href;
        this.parentId = parentId;
        this.sort = sort;
        this.value = value;
        this.type = type;
    }
}