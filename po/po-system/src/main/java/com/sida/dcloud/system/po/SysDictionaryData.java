/**
 * create by Administrator
 * @date 2017-10
 */
package com.sida.dcloud.system.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class SysDictionaryData extends BaseEntity implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("父字典ID")
    private Long parentId;

    @ApiModelProperty("数据字典分组ID")
    private Long groupId;

    @ApiModelProperty("数据字典分类编码")
    private String groupCode;

    @ApiModelProperty("字典键")
    private String dicCode;

    @ApiModelProperty("字典值")
    private String dicName;

    @ApiModelProperty("ID路径")
    private String path;

    @ApiModelProperty("排序（数字越大排序越后）")
    private Integer sort;

    @ApiModelProperty("机构ID（预留字段，做驾校隔离）")
    private String orgId;

    @ApiModelProperty("创建人")
    private String createdUser;

    @ApiModelProperty("创建人时间")
    private Date createdAt;

    @ApiModelProperty("更新人")
    private String updatedUser;

    @ApiModelProperty("更新时间")
    private Date lastUpdated;

    @ApiModelProperty("禁用标识（0可用，1禁用）")
    private Boolean disable;

    @ApiModelProperty("逻辑删除标识（0未删除，1已删除）")
    private Boolean deleteFlag;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode == null ? null : groupCode.trim();
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode == null ? null : dicCode.trim();
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
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
}