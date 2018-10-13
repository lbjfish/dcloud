package com.sida.dcloud.operation.common;

public class OperationException extends RuntimeException {
    public OperationException(String msg) {
        super(msg);
    }

    public OperationException(Exception e) {
        super(e);
    }
}
