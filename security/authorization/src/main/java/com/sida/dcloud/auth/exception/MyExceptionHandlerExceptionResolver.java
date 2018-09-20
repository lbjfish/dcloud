package com.sida.dcloud.auth.exception;

import com.alibaba.fastjson.JSON;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.vo.JsonResult;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(-1000)
public class MyExceptionHandlerExceptionResolver implements HandlerExceptionResolver {
    private static final String REFRESH_TOKEN_EXPIRED_MARK = "Invalid refresh token (expired): ";
    private static final String BAD_CREDENTIALS_MARK = "Bad credentials";
    private static final String MISSING_GRANT_TYPE_MARK = "Missing grant type";
    private static final String UNAUTHORIZED_GRANT_TYPE_MARK = "Unauthorized grant type: ";


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        JsonResult jsonResult = new JsonResult();
        if (e instanceof InvalidGrantException) {
            if (e.getMessage().startsWith(BAD_CREDENTIALS_MARK)) {
                jsonResult = JsonResult.buildError("用户名或者密码错误！", "9000101");
            }
        }
        else if (e instanceof InvalidTokenException) {
            if (e.getMessage().startsWith(REFRESH_TOKEN_EXPIRED_MARK)) {
                jsonResult = JsonResult.buildError("RefreshToken已失效，请重新登录！", "9000201");
            }
        }
        else if (e instanceof InvalidClientException) {
            if (e.getMessage().startsWith(UNAUTHORIZED_GRANT_TYPE_MARK)) {
                jsonResult = JsonResult.buildError("不支持的授权类型！", "9000301");
            }
        }
        else if (e instanceof InvalidRequestException) {
            if (e.getMessage().startsWith(MISSING_GRANT_TYPE_MARK)) {
                jsonResult = JsonResult.buildError("授权类型不能为空！", "9000401");
            }
        }
        else if (e instanceof ServiceException) {
            jsonResult.setStatus(false);
            jsonResult.setCode("2224");
            jsonResult.setMessage(e.getMessage());
        } else { // @Tung DispatchServlet#1027已经判空了。这里e不为null
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResult.setStatus(false);
            jsonResult.setCode("2225");
            jsonResult.setMessage(e.getMessage());
            jsonResult.setExceptionClass(e.getClass().getName());
            e.printStackTrace();
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Cache-Control","no-store");
        response.addHeader("Pragma","no-cache");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(jsonResult));
        } catch (IOException ex) {
            //logger.error("与客户端通讯异常：" + e.getMessage(), e);
            e.printStackTrace();
        }

        return new ModelAndView();
    }

}