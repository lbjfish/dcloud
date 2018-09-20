package com.sida.dcloud.system.aop;

import com.sida.xiruo.xframework.exception.BaseRuntimeException;
import com.sida.xiruo.xframework.exception.ControllerException;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.vo.JsonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yjr on 2017/8/26.
 * 已弃用，用ExceptionIntercepter代替
 */
//@Configuration
//@Aspect
@Deprecated
public class  SystemControllerInterceptor {
    @Pointcut("execution(* com.sida.dcloud.system.controller..*Controller.*(..))")
    private void systemPointcut() {

    }




    @Around(value = "SystemControllerInterceptor.systemPointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object object=null;
        try {
            JsonResult jsonResult = new JsonResult();
            try{
                object = proceedingJoinPoint.proceed();
                return object;
            } catch(BaseRuntimeException e){
                jsonResult.setStatus(false);
                if(e instanceof ServiceException||e instanceof ControllerException) {
                    jsonResult.setMessage(e.getErrorMessage());
                    jsonResult.setCode(e.getCode());
                }
                return jsonResult;
            }catch (Exception e){
                    e.printStackTrace();
                    jsonResult.setCode("0000");
                    jsonResult.setMessage("服务器内部错误");
                    jsonResult.setStatus(false);
                    return jsonResult;

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }





}
