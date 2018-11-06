package com.sida.dcloud.job;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/***
 * Crete by
 */
@SpringCloudApplication
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.sida.xiruo.xframework.cache.redis",
        "com.sida.xiruo.xframework.lock",
        "com.sida.xiruo.xframework.common.aop",
        "com.sida.xiruo.xframework.common.config",
//        "com.sida.dcloud.service.event.ext",
//        "com.sida.dcloud.service.event.config",
        "com.sida.dcloud.job"})
public class JobApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(JobApplication.class).run(args);
    }

    @Value("${common.job.token}}")
    private String token;

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjpbXSwiY29kZSI6MjAwLCJkYXRhIjoiIiwidXNlcl9uYW1lIjoiYWRtaW4iLCJtb2JpbGUiOiJhZG1pbiIsInJvbGVMaXN0IjpbeyJyb2xlSWQiOiIxMDAiLCJyb2xlQ29kZSI6IkFETUlOIiwicm9sZU5hbWUiOiLlubPlj7DnrqHnkIblkZgifV0sIm1lc3NhZ2UiOiIiLCJhdXRob3JpdGllcyI6WyLlubPlj7DnrqHnkIblkZgiXSwiY2xpZW50X2lkIjoiYWNtZSIsInRvdGFsIjowLCJ1c2VyX2lkIjoiMTAwIiwic2NvcGUiOlsib3BlbmlkIl0sInVzZXJBY2NvdW50IjoiYWRtaW4iLCJuYW1lIjoi566h55CG5ZGYIiwiaGVhZGVyIjoiaHR0cDovL3B1Yi5xaW5pdS5idXRvbmd0ZWNoLmNvbS9kZWZ1bHRfcGhvdG9AM3gucG5nIiwiaWQiOiIxMDAiLCJqdGkiOiJlYmIyMzdjZS1hNjI2LTQzM2YtOWU5MC1hM2U1NjNmMzQ2YmMiLCJzdGF0dXMiOnRydWV9.TEB-b-VfT7-zEDwT9vYWTg0sgogcKy6TmXPVgiyRKY8CksA2GDB0CsW070cdgQxNCvBX3fFDs2No_TyFLyzmNlskHiVeIC79LewgAR7e24EwfEuqIfXNz5n-BlUk4KRaIUzYe8VksyJOPX3ywo4l0wqFi7j5X7HRTuHwUCz1qYh5_AgSrul2vqjhr0yuG5J4UNKsWp9suCwsAgCoHrdMs3NH0bKFOAQBuabEwKgj3F2b67j2HFEia4ayy45jylfwXnEdh5zog3ptU8HQFOpFNLYkyp_HRkxFrIW48CQALUKAO7WtvVacCTQcPklYuWL1EepFDZem1t2NKQN6HX8vPg";
//                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
//                System.out.println(details.getTokenValue());
                requestTemplate.header("Authorization","bearer "+token);
            }
        };
    }
}