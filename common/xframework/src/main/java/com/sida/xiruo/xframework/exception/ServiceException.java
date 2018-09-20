package com.sida.xiruo.xframework.exception;


/**
 * 与用户操作或实际业务逻辑相关的异常<br>
 * 由框架统一捕获并处理,将异常信息显示给用户
 * @author yjr
 * @version 2017-08-26
 */
public class ServiceException extends BaseRuntimeException {

    private static final long	serialVersionUID	= -4942849136285353014L;

    public ServiceException() {
        super("500");
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Object code, String message) {
        super(code, message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(Throwable cause, Object code) {
        super(cause, code);
    }

}
