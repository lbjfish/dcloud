/**
 * create by jianglingfeng
 * @date 2018-09
 */
package com.sida.dcloud.activity.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ActivityRelHonored extends BaseEntity implements Serializable {
    @ApiModelProperty("嘉宾id（关联activity.honored_guest的id）")
    private String honoredGuestId;

    @ApiModelProperty("活动安排id（关联activity.activity_schedule的id）")
    private String scheduleId;

    @ApiModelProperty("排序号（数字越小越靠前）")
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public String getHonoredGuestId() {
        return honoredGuestId;
    }

    public void setHonoredGuestId(String honoredGuestId) {
        this.honoredGuestId = honoredGuestId == null ? null : honoredGuestId.trim();
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId == null ? null : scheduleId.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}