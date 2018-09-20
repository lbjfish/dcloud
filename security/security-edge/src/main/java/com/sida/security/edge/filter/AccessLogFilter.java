package com.sida.security.edge.filter;

import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.dcloud.system.po.SysAccessLogDetail;
import com.sida.security.edge.client.SystemClientInvoker;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.sida.xiruo.xframework.util.UUIDGenerate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Component
@WebFilter
public class AccessLogFilter implements Filter {

    @Resource
    private SystemClientInvoker systemClientInvoker;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        addAccessLog(request);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    /**
     * 记录用户访问
     * @param request
     */
    private void addAccessLog(HttpServletRequest request) {
        try {
            String url = request.getRequestURL().toString();
            if(!url.contains("swagger") && !url.contains("api-docs") && !url.contains("health")) {
                SysUserVo user = LoginManager.getUser();
                SysAccessLogDetail detail = new SysAccessLogDetail();
                detail.setId(UUIDGenerate.getNextId());
                detail.setUrl(url);
                detail.setClientIp(request.getRemoteHost());
                detail.setAccessDate(new Date());
                detail.setUserId(user.getId());
                detail.setUserAccount(user.getUserAccount());
                detail.setUserName(user.getName());
                systemClientInvoker.insertAccessLogDetail(detail);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}


