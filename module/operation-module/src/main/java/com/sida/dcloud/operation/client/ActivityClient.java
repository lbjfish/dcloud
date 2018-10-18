package com.sida.dcloud.operation.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "${provider.activity-module}")
public interface ActivityClient {
    @RequestMapping(value = "/sysUserCustomer/insertDto", method = RequestMethod.POST)
    @ApiOperation(value = "同步用户数据到系统用户表")
    Object insertDto(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

    @RequestMapping(value = "/sysUserCustomer/updateMobile", method = RequestMethod.POST)
    @ApiOperation(value = "修改手机号")
    Object updateMobile(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

    @RequestMapping(value = "/sysUserCustomer/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户资料")
    Object updateUserInfo(@RequestBody @ApiParam("JSON参数") Map<String, String> map);
}
