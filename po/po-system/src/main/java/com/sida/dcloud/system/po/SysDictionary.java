package com.sida.dcloud.system.po;

import com.sida.xiruo.po.common.BaseEntity;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysDictionary extends BaseEntity implements Serializable {
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

    @ApiModelProperty("字典键值")
    private String dicKey;

    @ApiModelProperty("字典值")
    private String dicValue;

    @ApiModelProperty("字典父id")
    private String parentId;

    @ApiModelProperty("等级id")
    private String levelId;

    @ApiModelProperty("id路径")
    private String path;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("排序（数字越大排序越后）")
    private Integer sort;

    @ApiModelProperty("类型（第一级type为0，其余存父级id）")
    private String type;

    @ApiModelProperty("子资源")
    List<SysDictionary> children = Lists.newArrayList();

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

    public String getDicKey() {
        return dicKey;
    }

    public void setDicKey(String dicKey) {
        this.dicKey = dicKey == null ? null : dicKey.trim();
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue == null ? null : dicValue.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId == null ? null : levelId.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public List<SysDictionary> getChildren() {
        return children;
    }

    public void setChildren(List<SysDictionary> children) {
        this.children = children;
    }
}