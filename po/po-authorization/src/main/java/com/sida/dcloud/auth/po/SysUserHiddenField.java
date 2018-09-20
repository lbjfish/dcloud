package com.sida.dcloud.auth.po;

import com.sida.xiruo.po.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SysUserHiddenField extends BaseEntity implements Serializable {
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("需要隐藏的字段id")
    private String hiddenFieldId;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getHiddenFieldId() {
        return hiddenFieldId;
    }

    public void setHiddenFieldId(String hiddenFieldId) {
        this.hiddenFieldId = hiddenFieldId == null ? null : hiddenFieldId.trim();
    }
}