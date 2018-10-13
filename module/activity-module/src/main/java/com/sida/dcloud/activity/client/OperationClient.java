package com.sida.dcloud.activity.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${provider.operation-module}")
//@FeignClient(name = "${provider.operation-module}", fallback = CommonUserHystrix.class)
public interface OperationClient {
    @RequestMapping(value = "/commonUser/findCommonUserById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id找普通用户信息")
    Object findCommonUserById(@RequestParam("userId") @ApiParam("用户id") String userId);
}
