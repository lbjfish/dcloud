package com.sida.xiruo.xframework.common.config;

import com.sida.xiruo.xframework.common.aop.AllowCrossDomainInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//改用过滤器方式，见CorsConfig
//@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AllowCrossDomainInterceptor allowCrossDomainInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String apiUri = "/**";
        //跨域拦截
        registry.addInterceptor(allowCrossDomainInterceptor).addPathPatterns(apiUri);
    }

    // 跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH").allowCredentials(true).maxAge(3600);
    }
}
