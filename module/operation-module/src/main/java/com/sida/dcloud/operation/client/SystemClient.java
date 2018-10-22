package com.sida.dcloud.operation.client;

import com.sida.dcloud.auth.vo.InitSystemDTO;
import com.sida.dcloud.auth.vo.UserConditionDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "${provider.system-module}", fallback = SystemClientHystrix.class)
public interface SystemClient {
    @RequestMapping(value = "/sysUserCustomer/saveOrUpdateDto", method = RequestMethod.POST)
    @ApiOperation(value = "同步用户数据到系统用户表")
    Object saveOrUpdateDto(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

    @RequestMapping(value = "/sysUserCustomer/updateMobile", method = RequestMethod.POST)
    @ApiOperation(value = "修改手机号")
    Object updateMobile(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

    @RequestMapping(value = "/sysUserCustomer/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户资料")
    Object updateUserInfo(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

    @RequestMapping(value = "/sysUserCustomer/updateUserPassword", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户密码")
    Object updateUserPassword(@RequestBody @ApiParam("JSON参数") Map<String, String> map);

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
