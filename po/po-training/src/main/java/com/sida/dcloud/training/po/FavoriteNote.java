/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class FavoriteNote extends BaseEntity implements Serializable {
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("习题id")
    private String exerciseId;

    @ApiModelProperty("收藏时间")
    private Date thatTime;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId == null ? null : exerciseId.trim();
    }

    public Date getThatTime() {
        return thatTime;
    }

    public void setThatTime(Date thatTime) {
        this.thatTime = thatTime;
    }
}