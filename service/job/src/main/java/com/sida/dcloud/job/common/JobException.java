package com.sida.dcloud.job.common;

import com.sida.xiruo.xframework.exception.BaseRuntimeException;

public class JobException extends BaseRuntimeException {
    private static final long	serialVersionUID	= 1L;

    public JobException() {
        super("500");
    }

    public JobException(String message) {
        super(message);
    }

    public JobException(Object code, String message) {
        super(code, message);
    }

    public JobException(Throwable cause) {
        super(cause);
    }

    public JobException(Throwable cause, Object code) {
        super(cause, code);
    }
}
