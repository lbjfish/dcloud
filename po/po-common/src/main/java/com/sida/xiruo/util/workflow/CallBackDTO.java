package com.sida.xiruo.util.workflow;

import io.swagger.annotations.ApiModelProperty;

public class CallBackDTO {

    @ApiModelProperty("业务主键id")
    private String id;
    @ApiModelProperty("最终结果类型 0:不同意，1:同意")
    private String approveResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult;
    }
}
