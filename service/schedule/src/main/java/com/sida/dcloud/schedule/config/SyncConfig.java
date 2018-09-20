package com.sida.dcloud.schedule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SyncConfig {
    @Value("${hrsync.alldate}")
    private String allDate;
    @Value("${hrsync.token}")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Value("${quartz.url}")
    private String url;
    @Value("${quartz.username}")
    private String userName;
    @Value("${quartz.password}")
    private String password;
    @Value("${quartz.driver-class-name}")
    private String driverClassName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getAllDate() {
        return allDate;
    }

    public void setAllDate(String allDate) {
        this.allDate = allDate;
    }
}
