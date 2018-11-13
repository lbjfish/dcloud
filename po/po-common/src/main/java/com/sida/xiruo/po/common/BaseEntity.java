package com.sida.xiruo.po.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Xiruo on 2017/7/1.
 */
public abstract class BaseEntity implements Serializable {

    public final static int USE_FLAG_INVALID = 0;
    public final static int USE_FLAG_VALID = 1;

    protected java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

    /**
     *
     */
    private static final long serialVersionUID = -8312183615633798727L;

    @ApiModelProperty("页码")
    private java.lang.Integer p = 0;

    @ApiModelProperty("每页条数")
    private java.lang.Integer s = 20;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private java.lang.Long recordCount;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private java.lang.Integer listSize;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Integer start;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Integer limit;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private java.lang.Integer listItemSize;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private java.lang.Integer pageItemSize;

//    @JsonIgnore
    @ApiModelProperty("关键字查询")
    private String query;

    @ApiModelProperty("权限等级（0-所有，1-片区，2-门店）")
    private Integer permissionLevel;
    @ApiModelProperty("权限属性id")
    private String permissionOrgId;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String filter;

    private List<String> ids;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String stringIds;

    @ApiModelProperty(hidden = true)
    private String orderField;

    @ApiModelProperty(hidden = true)
    private String orderString;

    //extjs support
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Set<Long> Itemselector;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Object sort;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String command;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String extra;

    @ApiModelProperty("访问类型")
    private Integer reqType;

    @JsonIgnore
    private boolean filterable = true;
    @JsonIgnore
    private boolean humpToUnderline = true;//FilterHelper, sort排序时, 是否将排序字段名驼峰转下划线

    public Integer getReqType() {
        return reqType;
    }

    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
        this.s = s;
    }

    public java.lang.Long getRecordCount() {
        return recordCount;
    }
    public void setRecordCount(java.lang.Long recordCount) {
        this.recordCount = recordCount;
    }
    public java.lang.Integer getListSize() {
        return listSize;
    }
    public void setListSize(java.lang.Integer listSize) {
        this.listSize = listSize;
    }
    public java.lang.Integer getListItemSize() {
        return listItemSize;
    }
    public void setListItemSize(java.lang.Integer listItemSize) {
        this.listItemSize = listItemSize;
    }
    public java.lang.Integer getPageItemSize() {
        return pageItemSize;
    }
    public void setPageItemSize(java.lang.Integer pageItemSize) {
        this.pageItemSize = pageItemSize;
    }
    public List<String> getIds() {
        return ids;
    }
    public void setIds(List<String> ids) {
        this.ids = ids;
    }
    public String getStringIds() {
        return stringIds;
    }
    public void setStringIds(String stringIds) {
        this.stringIds = stringIds;
    }
    public String getOrderField() {
        return orderField;
    }
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
    public String getOrderString() {
        return orderString;
    }
    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public String getFilter() {
        return filter;
    }
    public void setFilter(String filter) {
        this.filter = filter;
    }
    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public Object getSort() {
        return sort;
    }
    public void setSort(Object sort) {
        this.sort = sort;
    }
    public String getExtra() {
        return extra;
    }
    public void setExtra(String extra) {
        this.extra = extra;
    }
    public Set<Long> getItemselector() {
        return Itemselector;
    }
    public void setItemselector(Set<Long> itemselector) {
        Itemselector = itemselector;
    }
    public Integer getStart() {
        return start;
    }
    public void setStart(Integer start) {
        this.start = start;
    }
    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public boolean getHumpToUnderline() {
        return humpToUnderline;
    }

    public void setHumpToUnderline(boolean humpToUnderline) {
        this.humpToUnderline = humpToUnderline;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getPermissionOrgId() {
        return permissionOrgId;
    }

    public void setPermissionOrgId(String permissionOrgId) {
        this.permissionOrgId = permissionOrgId;
    }


    //以下公共字段
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("机构id")
    private String orgId;
    @ApiModelProperty("创建用户id")
    private String createdUser;
    @ApiModelProperty("创建时间")
    private Date createdAt;
    @ApiModelProperty("最后更新用户id")
    private String updatedUser;
    @ApiModelProperty("最后更新事件")
    private Date lastUpdated;
    @ApiModelProperty("是否失效")
    private Boolean disable;
    @ApiModelProperty("是否删除")
    private Boolean deleteFlag;
    @ApiModelProperty("备用字段1")
    private String redString1;
    @ApiModelProperty("备用字段2")
    private String redString2;
    @ApiModelProperty("备用字段3")
    private String redString3;
    @ApiModelProperty("备用字段4")
    private String redString4;
    @ApiModelProperty("备用字段5")
    private String redString5;
    @ApiModelProperty("备用字段6")
    private String redString6;

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
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
        this.updatedUser = updatedUser;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Boolean getsDisable() {
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

    public String getRedString1() {
        return redString1;
    }

    public void setRedString1(String redString1) {
        this.redString1 = redString1;
    }

    public String getRedString2() {
        return redString2;
    }

    public void setRedString2(String redString2) {
        this.redString2 = redString2;
    }

    public String getRedString3() {
        return redString3;
    }

    public void setRedString3(String redString3) {
        this.redString3 = redString3;
    }

    public String getRedString4() {
        return redString4;
    }

    public void setRedString4(String redString4) {
        this.redString4 = redString4;
    }

    public String getRedString5() {
        return redString5;
    }

    public void setRedString5(String redString5) {
        this.redString5 = redString5;
    }

    public String getRedString6() {
        return redString6;
    }

    public void setRedString6(String redString6) {
        this.redString6 = redString6;
    }
}
