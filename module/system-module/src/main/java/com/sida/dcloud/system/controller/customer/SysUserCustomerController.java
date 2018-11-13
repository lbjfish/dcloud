package com.sida.dcloud.system.controller.customer;

import com.sida.dcloud.system.controller.SysUserController;
import com.sida.dcloud.system.service.SysUserService;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("sysUserCustomer")
@Api(description = "用户管理api To Customer")
public class SysUserCustomerController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysUserCustomerController.class);

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/saveOrUpdateDto", method = RequestMethod.POST)
    @ApiOperation(value = "同步用户数据到系统用户表，其他服务调用")
    public Object saveOrUpdateDto(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        //CommonUserOperation
        return toResult(sysUserService.saveOrUpdateDto(map));
    }

    @RequestMapping(value = "/updateMobile", method = RequestMethod.POST)
    @ApiOperation(value = "修改新手机号，其他服务调用")
    public Object updateMobile(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        return toResult(sysUserService.updateMobile(map));
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户资料")
    public Object updateUserInfo(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        return toResult(sysUserService.updateUserInfo(map));
    }

    @RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户密码")
    public Object updateUserPassword(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        return toResult(sysUserService.updateUserPassword(map));
    }

    @RequestMapping(value = "/unbindThirdPartAccount", method = RequestMethod.GET)
    @ApiOperation(value = "解绑第三方账号")
    Object unbindThirdPartAccount(@RequestParam("loginFrom") @ApiParam("第三方账号类型：1. 微信，2. 阿里， 3. QQ ...") String loginFrom, @RequestParam("mobile") @ApiParam("手机号码") String mobile) {
        return toResult(sysUserService.unbindThirdPartAccount(loginFrom, mobile));
    }

    @RequestMapping(value = "/bindThirdPartAccount", method = RequestMethod.POST)
    @ApiOperation(value = "绑定第三方账号")
    Object bindThirdPartAccount(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        return toResult(sysUserService.bindThirdPartAccount(map));
    }

    @RequestMapping(value = "/testDistributeTransaction", method = RequestMethod.GET)
    @ApiOperation(value = "测试分布式事务")
    Object testDistributeTransaction(@RequestParam("id") @ApiParam("id") String id, @RequestParam("remark") @ApiParam("描述") String remark) {
        return toResult(sysUserService.testDistributeTransaction(id, remark));
    }
}
