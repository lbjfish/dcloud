package com.sida.dcloud.auth.config;

import com.sida.dcloud.auth.exception.MyExceptionHandlerExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@EnableWebMvc
@Configuration
public class WebMVCConfiguration extends WebMvcConfigurationSupport {
    @Bean
    public HandlerExceptionResolver handlerExceptionResolver() {
        MyExceptionHandlerExceptionResolver exceptionResolver = new MyExceptionHandlerExceptionResolver();
        return exceptionResolver;
    }

}
