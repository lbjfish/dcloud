package com.sida.xiruo.xframework.vo;

import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by Xiruo on 2017/7/20.
 */
public class JsonResult implements java.io.Serializable {

    @ApiModelProperty("状态标识：成功true，失败false")
    private boolean status = true;

    @ApiModelProperty("状态码：默认为200 表示请求成功")
    private Object code=200;

    @ApiModelProperty("总行数：用于列表查询")
    private long total;

    @ApiModelProperty("消息：业务处理成功或失败后的内容提示")
    private String message;	//成功/错误消息提示

    @ApiModelProperty("数据结果集：请求后的响应数据内容")
    private Object data;	//数据包内容

    @ApiModelProperty("是否进行Base64编码")
    private boolean encode;	//是否进行Base64编码

    @JsonIgnore
    @ApiModelProperty(value = "异常类名", hidden = true)
    private String exceptionClass;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isEncode() {
        return encode;
    }

    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    //构建一个成功的数据区域
    public static JsonResult buildOk(Object datas) {
        JsonResult result=new JsonResult();
        result.setStatus(true);
        result.setData(datas);
        return result;
    }
    //构建一个失败的结果
    public static JsonResult buildError(String msg){
        JsonResult result=new JsonResult();
        result.setStatus(false);
        result.setMessage(msg);
        return result;
    }
    //构建一个失败的结果
    public static JsonResult buildError(String msg,Object code){
        JsonResult result=new JsonResult();
        result.setStatus(false);
        result.setMessage(msg);
        result.setCode(code);
        return result;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "status=" + status +
                ", code=" + code +
                ", total=" + total +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}