package com.sida.dcloud.operation.common;

import com.sida.xiruo.xframework.exception.BaseRuntimeException;

public class OperationException extends BaseRuntimeException {
    private static final long	serialVersionUID	= 1L;

    public OperationException() {
        super("500");
    }

    public OperationException(String message) {
        super(message);
    }

    public OperationException(Object code, String message) {
        super(code, message);
    }

    public OperationException(Throwable cause) {
        super(cause);
    }

    public OperationException(Throwable cause, Object code) {
        super(cause, code);
    }
}
