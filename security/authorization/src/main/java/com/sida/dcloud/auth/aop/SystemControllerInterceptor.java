package com.sida.dcloud.auth.aop;

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
 */
@Configuration
@Aspect
public class SystemControllerInterceptor {
    @Pointcut("execution(* com.sida.dcloud.auth.controller..*Controller.*(..))")
    private void systemPointcut() {

    }




    @Around(value = "com.sida.dcloud.auth.aop.SystemControllerInterceptor.systemPointcut()")
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
                    jsonResult.setMessage("服务器内部错误"+e.getMessage());
                    return jsonResult;

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }





}
