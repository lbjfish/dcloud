package com.sida.xiruo.xframework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sida.xiruo.xframework.exception.BaseRuntimeException;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.vo.JsonResult;
import com.netflix.client.ClientException;
import feign.FeignException;
import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by huangbaidong
 * 2018-02-28
 * 异常工具类
 */
public class ExceptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    /**
     * 从异常信息中提取错误信息
     * From  * @param e
     * @return
     */
    public static JsonResult getJsonResultByException(Throwable e) {
        String errorMessage = null;
        Object errorCode = "500";
        String exceptionClass = e.getClass().getName();
        if(e instanceof FeignException){
            errorMessage = e.getMessage();
        }else if(e instanceof BaseRuntimeException){
            errorMessage = e.getMessage();
            errorCode = ((BaseRuntimeException) e).getCode();
        }else if(e instanceof ActivitiException){
            errorMessage = e.getMessage();
            if(BlankUtil.isEmpty(errorMessage)) {
                if (BlankUtil.isNotEmpty(e.getCause())) {
                    Throwable cause = e.getCause().getCause();
                    if (cause instanceof ServiceException) {
                        ServiceException s = (ServiceException) cause;
                        errorMessage = s.getErrorMessage();
                        errorCode = s.getCode();
                    }
                } else {
                    errorMessage = "流程异常：" + e.getMessage();
                }
            }
        }else if(e instanceof ClientException){
            errorMessage = "网络异常！";
        } else {
            logger.error(e.getMessage(), e);
            errorMessage = "服务器内部错误";
        }

        //用正则表达式取出自定义异常信息（Feign调用异常返回的是FeignException会把自定义异常信息在封装起来）
        if(BlankUtil.isNotEmpty(errorMessage)) {
            Pattern pattern = Pattern.compile("\\{.*}$");
            Matcher matcher = null;
            matcher = pattern.matcher(errorMessage);
            if (matcher.find()) {
                JSONObject jsonObject = JSON.parseObject(matcher.group());
                errorCode = jsonObject.getString("code");
                errorMessage = jsonObject.getString("message");
                exceptionClass = jsonObject.getString("exceptionClass");
            }
            if(errorMessage.startsWith("[")&&errorMessage.endsWith("]")){
                errorMessage=errorMessage.substring(1,errorMessage.length()-1).replaceAll(",","");
            }
        }

        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(false);
        jsonResult.setCode(errorCode);
        jsonResult.setMessage(errorMessage);
        jsonResult.setExceptionClass(exceptionClass);

        return jsonResult;
    }

}
