package com.sida.dcloud.activity.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/****
 * feignClient超时设置
 */
@Configuration
public class FeignClientRequstTimeConfig {
    @Bean
    Request.Options feignOptions() {
        return new Request.Options(3 * 1000, 60 * 1000);
    }
    @Bean
    Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }
}
