/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.vo;

import com.sida.xiruo.po.common.UserCentricDTO;
import io.swagger.annotations.ApiModelProperty;

public class HonoredGuestVo extends UserCentricDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("英文名")
    private String engName;

    @ApiModelProperty("昵称")
    private String alias;

    @ApiModelProperty("所属国家/地区（关联system.sys_region表id）")
    private String nationRegionId;

    @ApiModelProperty("头衔")
    private String honor;

    @ApiModelProperty("身份证号码")
    private String idNo;

    @ApiModelProperty("移动电话号码")
    private String mobile;

    @ApiModelProperty("排序值（越小越靠前）")
    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName == null ? null : engName.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getNationRegionId() {
        return nationRegionId;
    }

    public void setNationRegionId(String nationRegionId) {
        this.nationRegionId = nationRegionId == null ? null : nationRegionId.trim();
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor == null ? null : honor.trim();
    }
}