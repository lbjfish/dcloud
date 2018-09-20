package com.sida.dcloud.system.common.dto;

import org.openqa.selenium.WebDriver;

/**
 * Created by xieguopei on 2017/8/9.
 */
public class CollectorWebDriver {
    private WebDriver webDriver;
    private Boolean status;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
