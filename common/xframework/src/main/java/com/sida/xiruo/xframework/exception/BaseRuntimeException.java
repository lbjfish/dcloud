package com.sida.xiruo.xframework.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * 基本异常类，初始化基本返回类型
 * @author
 * @date 2017年3月3日
 */
public class BaseRuntimeException extends HystrixBadRequestException {

    private static final long	serialVersionUID	= -3637172659954598718L;

    private Object					code;
    private String errorMessage;

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(Object code, String message) {
        super(message);
        this.code = code;
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public BaseRuntimeException(Throwable cause, Object code) {
        this(cause);
        this.code = code;
    }


    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return getMessage();
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
