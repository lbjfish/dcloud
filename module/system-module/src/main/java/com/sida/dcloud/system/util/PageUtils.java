package com.sida.dcloud.system.util;

import com.sida.dcloud.system.common.CollectorConstants;
import com.sida.dcloud.system.common.dto.CollectorControl;
import com.sida.dcloud.system.common.pool.DriverPool;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.List;


/**
 * Created by xieguopei on 2017/8/1.
 */
public class PageUtils {
    private static WebDriver webDriver;

//    public static WebDriver getWebDriver(Page page) {
//        System.setProperty("webdriver.firefox.bin", "D:\\日常\\firefox\\firefox.exe");
//
//        if(null == webDriver) {
////            webDriver = new PhantomJSDriver();
//            webDriver = new FirefoxDriver();
//        }
//
//        webDriver.get(page.getUrl());
//
//        return webDriver;
//    }

    public static WebDriver getWebDriver(String url) {
        System.setProperty("phantomjs.binary.path", "E:\\phantomjs\\bin\\phantomjs.exe");

        if(null == webDriver) {
            webDriver = new PhantomJSDriver();
        }

        webDriver.get(url);

        return webDriver;
    }

    /**
     * 爬取页面对应控制信息
     * @author xieguopei
     * @date 2017/08/09
     * @param url
     * @return
     * @throws InterruptedException
     */
    public static List<CollectorControl> getControlMes(String url) throws InterruptedException {
        List<CollectorControl> collectorControls = Lists.newLinkedList();
        WebDriver webDriver;
        WebElement paneElement;
        String clzType;

        webDriver = DriverPool.getDriverMes();

        webDriver.navigate().to(url);

        Thread.sleep(CollectorConstants.LONGSLEEPTIME);

        paneElement = webDriver.findElement(By.className("el-tab-pane"));

        clzType = paneElement.getAttribute("clz-type");

        switch (clzType) {
            case "form":

                collectorControls = getControlMesWithForm(webDriver);

                break;
            case "table":
                break;
            case "none":
                break;
            default:
        }

        return collectorControls;
    }

    /**
     * 获取表单对应的控制信息
     * @author xieguopei
     * @date 2017/08/09
     * @return
     */
    private static List<CollectorControl> getControlMesWithForm(WebDriver webDriver) {
        List<CollectorControl> collectorControls = Lists.newLinkedList();
        List<WebElement> webElements = Lists.newLinkedList();
        WebElement btnGroupElement;
        CollectorControl dumpControl;
        String dumpName;
        String dumpClassName;
        String dumpId;

        btnGroupElement = webDriver.findElement(By.className("btn-box"));

        webElements = btnGroupElement.findElements(By.tagName("button"));

        for(WebElement element : webElements) {
            dumpControl = new CollectorControl();

            dumpName = element.getText();
            dumpClassName = element.getAttribute("class");
            dumpId = element.getAttribute("id");

            dumpControl.setControlClass(dumpClassName);
            dumpControl.setControlId(dumpId);
            dumpControl.setControlName(dumpName);
            dumpControl.setType(CollectorConstants.controlType.BUTTON.name());

            collectorControls.add(dumpControl);
        }

        return collectorControls;
    }

    /**
     * 获取列表对应的控制信息
     * @author xieguopei
     * @date 2017/08/09
     * @return
     */
    private List<CollectorControl> getControlMesWithTable(WebDriver webDriver) {
        List<CollectorControl> collectorControls = Lists.newLinkedList();

        return collectorControls;
    }


}
