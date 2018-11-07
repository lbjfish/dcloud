package com.sida.dcloud.job.client;

import com.sida.dcloud.job.common.JobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActivityClientHystrix implements ActivityClient {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityClientHystrix.class);

    @Override
    public Object resendThirdPartCode() {
        LOG.warn("进入断路器-resendThirdPartCode。。。");
        throw new JobException("resendThirdPartCode 失败.");
    }

    @Override
    public Object selectUnpayOrderList() {
        LOG.warn("进入断路器-selectUnpayOrderList。。。");
        throw new JobException("selectUnpayOrderList 失败.");
    }

    @Override
    public Object scanAndChangeOrderStatus() {
        LOG.warn("进入断路器-scanAndChangeOrderStatus。。。");
        throw new JobException("scanAndChangeOrderStatus 失败.");
    }

    @Override
    public Object updateOrderStatus(String orderId, String orderStatus) {
        LOG.warn("进入断路器-updateOrderStatus。。。");
        throw new JobException("updateOrderStatus 失败.");
    }
}
