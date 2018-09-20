package com.sida.xiruo.po.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用接口回参格式
 * @param <T>
 * @author wuzhenwei
 */
@ApiModel(description = "通用接口回参格式")
public class BaseDTO<T>{

    @ApiModelProperty(value = "接口请求状态：true.请求成功，false.请求失败")
    private Boolean status;

    @ApiModelProperty(value = "是否加密：true.加密，false.不加密")
    private Boolean encode = false;

    @ApiModelProperty(value = "分页总条数")
    private Long total;

    @ApiModelProperty(value = "返回码：'200'.请求成功，其他编码.错误码")
    private Integer code;

    @ApiModelProperty(value = "返回信息：附加信息，用于提供给前端的提示信息")
    private String message;

    @ApiModelProperty(value = "业务信息")
    private T data;

    public BaseDTO(Boolean status) {
        this.status = status;
        if (status){
            this.code = 200;
            this.message = "操作成功！";
        }
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getEncode() {
        return encode;
    }

    public void setEncode(Boolean encode) {
        this.encode = encode;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "status=" + status +
                ", encode=" + encode +
                ", total=" + total +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
