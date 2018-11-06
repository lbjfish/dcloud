package com.sida.dcloud.job.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "${provider.activity-module}", fallback = ActivityClientHystrix.class)
public interface ActivityClient {
    @RequestMapping(value = "/customerActivitySignupNote/resendThirdPartCode", method = RequestMethod.GET)
    @ApiOperation(value = "扫描并发送校验码到第三方（未自动发送成功）")
    Object resendThirdPartCode();

    @RequestMapping(value = "/scanAndChangeOrderStatus", method = RequestMethod.GET)
    @ApiOperation(value = "扫描改变订单状态（包括从腾讯支付获取支付状态）")
    Object scanAndChangeOrderStatus();

    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.GET)
    @ApiOperation(value = "更新订单状态")
    Object updateOrderStatus(@RequestParam("orderId") @ApiParam("订单id")String orderId,
                             @RequestParam("orderStatus") @ApiParam("订单状态")String orderStatus);
}
