package com.sida.xiruo.discovery;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by Xiruo on 2017/5/17 0017.
 */
@EnableEurekaServer
@SpringBootApplication
public class DiscoveryApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApplication.class).web(true).run(args);
    }
}