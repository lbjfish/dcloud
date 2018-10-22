package com.sida.dcloud.content.common;

import com.sida.xiruo.xframework.exception.BaseRuntimeException;

public class ContentException extends BaseRuntimeException {
    private static final long	serialVersionUID	= 1L;

    public ContentException() {
        super("500");
    }

    public ContentException(String message) {
        super(message);
    }

    public ContentException(Object code, String message) {
        super(code, message);
    }

    public ContentException(Throwable cause) {
        super(cause);
    }

    public ContentException(Throwable cause, Object code) {
        super(cause, code);
    }
}
