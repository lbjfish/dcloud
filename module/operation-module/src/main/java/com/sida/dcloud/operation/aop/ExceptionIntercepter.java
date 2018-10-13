package com.sida.dcloud.operation.aop;

import com.sida.xiruo.xframework.util.ExceptionUtil;
import com.sida.xiruo.xframework.vo.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * created by huangbaidong
 * 2018-02-28
 * 统一异常处理类
 */
@RestControllerAdvice
public class ExceptionIntercepter {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionIntercepter.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult handleException(Throwable e) {
        return ExceptionUtil.getJsonResultByException(e);
    }

}
