package com.sida.dcloud.auth.filter;

import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.vo.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * author yjr
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidateCodeFilter extends OncePerRequestFilter {
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
//        if ("/oauth/token".equals(req.getServletPath()) && "password".equals(req.getParameter("grant_type"))) {
//            String key = req.getParameter("key");
//            if (BlankUtil.isEmpty(key)) {
//                outResWithErr("key不能为空",res);
//                return;
//            }
//            String imageCode = (String) redisUtil.get(key);
//            System.out.println(imageCode);
//            String validateCode = req.getParameter("imageCode");
//            if (BlankUtil.isEmpty(validateCode)) {
//                outResWithErr("验证码不能为空", res);
//                return;
//            } else if (!validateCode.equals(imageCode)) {
//                outResWithErr("验证码不正确", res);
//                return;
//            } else {
//                /*验证码正确，清除*/
//                redisUtil.remove(key);
//            }
//        }
        filterChain.doFilter(req, res);
    }

    private void outResWithErr(String msg, HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        res.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        JsonResult jr = JsonResult.buildError(msg, "2313");
        PrintWriter printWriter = res.getWriter();
        printWriter.write(objectMapper.writeValueAsString(jr));
        printWriter.flush();
    }
}
