package com.sida.dcloud.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Xiruo on 2017/7/17.
 */
@SpringCloudApplication
@MapperScan(basePackages = {"com.sida.dcloud.auth.dao"})
//@EnableResourceServer
//@EnableEurekaClient
@ComponentScan(basePackages = {"com.sida.xiruo.xframework.cache.redis","com.sida.dcloud.auth"})
public class AuthApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthApplication.class).web(true).run(args);
    }
}
