package com.sida.dcloud.activity.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "${provider.operation-module}", fallback = OperationClientHystrix.class)
//@FeignClient(name = "${provider.operation-module}", fallback = CommonUserHystrix.class)
public interface OperationClient {
    @RequestMapping(value = "/commonUser/findCommonUserById", method = RequestMethod.GET)
    @ApiOperation(value = "根据主键id获取C端用户信息")
    Object findCommonUserById(@RequestParam("userId") @ApiParam("用户id")String userId);

    @RequestMapping(value = "/commonUser/updateFaceUrl", method = RequestMethod.POST)
    @ApiOperation(value = "更新人脸图片地址")
    Object updateFaceUrl(@RequestBody @ApiParam("用户信息MAP") Map<String, String> map);

    @RequestMapping(value = "/companyInfo/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据企业主键id获取信息")
    Object findOne(@RequestParam("id") @ApiParam("id")String id);

    @RequestMapping(value = "/companyInfo/findMany", method = RequestMethod.GET)
    @ApiOperation(value = "根据企业主键ids获取信息")
    Object findMany(@RequestParam("ids") @ApiParam("ids")String ids);
}
