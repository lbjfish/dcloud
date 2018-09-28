package com.sida.dcloud.activity.common;

public class ActivityException extends RuntimeException {
    public ActivityException(String msg) {
        super(msg);
    }

    public ActivityException(Exception e) {
        super(e);
    }
}
