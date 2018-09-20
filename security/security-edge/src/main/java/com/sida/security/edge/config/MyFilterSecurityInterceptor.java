package com.sida.security.edge.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Tung 有问题：会在servlet容器层注册filter，也会在springSecurityFilterChain中注册，会多跑一遍权限检测浪费性能。
 * Created by Lv.Chen on 2017/9/19.
 */
@Service("filterSecurityInterceptor")
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {


    private AuthenticationManager authenticationManager;
    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void MyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager){
        super.setAccessDecisionManager(myAccessDecisionManager);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(servletRequest,servletResponse,filterChain);
        invoke(fi);
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource(){
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource){
        this.securityMetadataSource = newSource;
    }

    @Override
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }
    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}

