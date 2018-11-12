/**
 * create by jianglingfeng
 * @date 2018-11
 */
package com.sida.dcloud.operation.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

public class TagLibVo extends UserCentricDTO {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("标签组（关联tag_group表id字段）")
    private String groupId;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("别名")
    private String alias;

    @ApiModelProperty("英文名")
    private String engName;

    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }
}