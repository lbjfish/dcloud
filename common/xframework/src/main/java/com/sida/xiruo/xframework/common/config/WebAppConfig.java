package com.sida.xiruo.xframework.common.config;

import com.sida.xiruo.xframework.common.aop.AllowCrossDomainInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AllowCrossDomainInterceptor allowCrossDomainInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String apiUri = "/**";
        //跨域拦截
        registry.addInterceptor(allowCrossDomainInterceptor).addPathPatterns(apiUri);
    }

}
