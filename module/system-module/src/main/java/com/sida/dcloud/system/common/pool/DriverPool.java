package com.sida.dcloud.system.common.pool;

import com.sida.dcloud.system.common.dto.CollectorWebDriver;
import com.google.common.collect.Lists;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.List;

/**
 * Created by xieguopei on 2017/8/9.
 */
public class DriverPool {
    private int size;
    private static List<CollectorWebDriver> webDriverList = Lists.newLinkedList();

    public DriverPool(int size) {
        this.size = size;

        CollectorWebDriver collectorWebDriver;

        for(int i = 0; i < size; i++) {
            collectorWebDriver = new CollectorWebDriver();

            collectorWebDriver.setStatus(false);
            collectorWebDriver.setWebDriver(new PhantomJSDriver());

            webDriverList.add(collectorWebDriver);
        }
    }

    public static WebDriver getDriverMes() {
        Boolean status;

        for(CollectorWebDriver collectorWebDriver : webDriverList) {
            status = collectorWebDriver.getStatus();

            if(!status) {
                collectorWebDriver.setStatus(true);

                return collectorWebDriver.getWebDriver();
            }
        }

        return null;
    }
}
