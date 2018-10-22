package com.sida.dcloud.operation;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Optional;

/***
 * Crete by
 */
@SpringCloudApplication
@EnableFeignClients
@EnableResourceServer
@MapperScan(basePackages = {"com.sida.dcloud.operation.dao"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.sida.xiruo.xframework.cache.redis",
        "com.sida.xiruo.xframework.lock",
        "com.sida.xiruo.xframework.common",
//        "com.sida.dcloud.service.event.ext",
//        "com.sida.dcloud.service.event.config",
        "com.sida.dcloud.operation"})
public class OperationApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(OperationApplication.class).run(args);
    }

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                        .map(auth -> auth.getDetails()).ifPresent(details -> {
                    String token = ((OAuth2AuthenticationDetails) details).getTokenValue();
                    requestTemplate.header("Authorization", "bearer " + token);
                });
            }
        };
    }
}