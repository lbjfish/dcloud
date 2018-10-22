package com.sida.dcloud.activity.common;

import com.sida.xiruo.xframework.exception.BaseRuntimeException;

public class ActivityException extends BaseRuntimeException {
    private static final long	serialVersionUID	= 1L;

    public ActivityException() {
        super("500");
    }

    public ActivityException(String message) {
        super(message);
    }

    public ActivityException(Object code, String message) {
        super(code, message);
    }

    public ActivityException(Throwable cause) {
        super(cause);
    }

    public ActivityException(Throwable cause, Object code) {
        super(cause, code);
    }
}
