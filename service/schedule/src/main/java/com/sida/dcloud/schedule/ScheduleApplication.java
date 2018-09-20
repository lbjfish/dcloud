package com.sida.dcloud.schedule;

import com.sida.dcloud.schedule.config.SyncConfig;
import com.sida.dcloud.schedule.quartz.SpringContextUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@ComponentScan(basePackages = {"com.sida.dcloud.schedule","com.sida.xiruo.xframework.cache.redis"})
@MapperScan(basePackages = {"com.sida.dcloud.schedule.dao"})
public class ScheduleApplication {
	@Autowired
	private SyncConfig syncConfig;
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ScheduleApplication.class, args);
		SpringContextUtil.setApplicationContext(applicationContext);
	}

	@Bean
	public RequestInterceptor requestTokenBearerInterceptor(){
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				String token = syncConfig.getToken();
				//String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmdhbml6YXRpb25QYXRoIjpudWxsLCJyb2xlIjpbbnVsbF0sInVzZXJfbmFtZSI6IuWumuaXtuS7u-WKoem7mOiupOeUqOaItyIsInJvbGVJZCI6bnVsbCwiYXZhdGFyIjoid3d3LmJhaWR1LmNvbS9pbWcvYmRfbG9nbzEucG5nIiwiY2VydElkIjpudWxsLCJzdG9yZUlkIjpudWxsLCJjbGllbnRfaWQiOiJjbHpfMjAxNzEyMDciLCJvcmdhbml6YXRpb25JZCI6bnVsbCwiYXJlYUlkIjpudWxsLCJ1c2VyX2lkIjoidGVzdF9zeW5jaHJvbml6YXRpb24iLCJvcmdfaWQiOm51bGwsInNjb3BlIjpbIm9wZW5pZCJdLCJ1c2VyQWNjb3VudCI6InN5bmMiLCJyb2xlQ29kZSI6bnVsbCwibmFtZSI6IuWumuaXtuS7u-WKoem7mOiupOeUqOaItyIsInJvbGVOYW1lIjpudWxsLCJqdGkiOiIyNmU2ZjIzOS1mODZjLTQ2YzItYjA2Ni1jNzI4ZDVmNTY2ZDcifQ.IqUjk4tmquJnoaGkKAsvSHnQHsQwDG_4Ucn7ncHO4xhp5JHreNmZrgWaXcr2QpuV1l0zFJNl7MY2peYCBJ1kLW4Sz9LUjb2974L5_y0z3lnWlt4YKRBldv7_73cx7FTuKCpPwMoRuyou6BDJYbGWt78jRB3jXeU1JrhGsECZ1kFChPlgqZ2FCnHOUDc5sXsK13xxdKKVTIOMQnuAPeyLWkajxrGD7gLfcGQmq9FwLCwA7arzYClIpwOgR7FUQ_SvBKsDbEuWVJUdAWDWpTfNMUn9665yMc4URGNB8Z3_h7OKCJPVRHDCQqGnaHosGCKXCcGzDqdl77G4qaqPpBOztg";
				//OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
				requestTemplate.header("Authorization","bearer "+token);
			}
		};
	}
}
