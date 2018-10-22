package com.sida.dcloud.operation.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "${provider.content-module}", fallback = ContentClientHystrix.class)
public interface ContentClient {
    @RequestMapping(value = "/sysUserCustomer/insertDto", method = RequestMethod.POST)
    @ApiOperation(value = "同步用户数据到系统用户表")
    Object insertDto(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

    @RequestMapping(value = "/sysUserCustomer/updateMobile", method = RequestMethod.POST)
    @ApiOperation(value = "修改手机号")
    Object updateMobile(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

    @RequestMapping(value = "/sysUserCustomer/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户资料")
    Object updateUserInfo(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

    @RequestMapping(value = "/sysUserCustomer/unbindThirdPartAccount", method = RequestMethod.GET)
    @ApiOperation(value = "解绑第三方账号")
    Object unbindThirdPartAccount(@RequestParam("loginFrom") @ApiParam("第三方账号类型：1. 微信，2. 阿里， 3. QQ ...") String loginFrom, @RequestParam("mobile") @ApiParam("手机号码") String mobile);

    @RequestMapping(value = "/sysUserCustomer/bindThirdPartAccount", method = RequestMethod.POST)
    @ApiOperation(value = "绑定第三方账号")
    Object bindThirdPartAccount(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

    @RequestMapping(value = "/sysUserCustomer/testDistributeTransaction", method = RequestMethod.GET)
    @ApiOperation(value = "测试分布式事务")
    Object testDistributeTransaction(@RequestParam("id") @ApiParam("id") String id, @RequestParam("remark") @ApiParam("描述") String remark);
}
