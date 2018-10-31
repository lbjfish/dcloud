package com.sida.dcloud.job;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/***
 * Crete by
 */
@SpringCloudApplication
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.sida.xiruo.xframework.cache.redis",
        "com.sida.xiruo.xframework.lock",
        "com.sida.xiruo.xframework.common",
//        "com.sida.dcloud.service.event.ext",
//        "com.sida.dcloud.service.event.config",
        "com.sida.dcloud.job"})
public class JobApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(JobApplication.class).run(args);
    }
}