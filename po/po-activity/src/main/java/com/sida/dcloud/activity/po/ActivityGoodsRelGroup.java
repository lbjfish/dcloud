/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ActivityGoodsRelGroup extends BaseEntity implements Serializable {
    @ApiModelProperty("商品id（关联activity.activity_goods的id）")
    private String goodsId;

    @ApiModelProperty("组合id（关联activity.activity_goods_group的id）")
    private String groupId;

    @ApiModelProperty("排序号（数字越小越靠前）")
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}