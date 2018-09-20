package com.sida.xiruo.xframework.concurrent;

/**
 * 记录任务执行结果的JavaBean，用于Callable
 *
 * @author Tung
 * @version 1.0
 * @date 2018/2/26.
 * @update
 */

public class BatchResult {
    /**
     * 任务执行结果，true为执行成功，表示没有异常/异常已被处理。
     * false表示执行过程中产生了未处理的异常。
     */
    private Boolean success;
    /**
     * 执行过程中的异常。
     */
    private Exception exception;

    /**
     * 用于线程间传递参数。
     */
    private Object param;

    public BatchResult(Boolean success){
        this.success = success;
    }

    public BatchResult(Boolean success, Exception e) {
        this.success = success;
        this.exception = e;
    }

    public BatchResult(Boolean success, Exception exception, Object param) {
        this.success = success;
        this.exception = exception;
        this.param = param;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
