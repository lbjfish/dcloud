package com.sida.xiruo.xframework.exception;

/**
 * Created by  on 2017/3/3.
 * 控制层异常信息类
 */
public class ControllerException extends BaseRuntimeException {

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Object code, String message) {
        super(code, message);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

    public ControllerException(Throwable cause, Object code) {
        super(cause, code);
    }

}
