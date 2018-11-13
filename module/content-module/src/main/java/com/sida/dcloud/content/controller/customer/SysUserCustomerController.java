package com.sida.dcloud.content.controller.customer;

import com.sida.dcloud.content.service.SysUserContentService;
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
    private SysUserContentService sysUserContentService;

    @RequestMapping(value = "/insertDto", method = RequestMethod.POST)
    @ApiOperation(value = "同步用户数据到系统用户表，其他服务调用")
    public Object insertDto(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        //CommonUserOperation
        return toResult(sysUserContentService.insertDto(map));
    }

    @RequestMapping(value = "/updateMobile", method = RequestMethod.POST)
    @ApiOperation(value = "修改新手机号，其他服务调用")
    public Object updateMobile(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        return toResult(sysUserContentService.updateMobile(map));
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户资料")
    public Object updateUserInfo(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        return toResult(sysUserContentService.updateUserInfo(map));
    }

    @RequestMapping(value = "/unbindThirdPartAccount", method = RequestMethod.GET)
    @ApiOperation(value = "解绑第三方账号")
    Object unbindThirdPartAccount(@RequestParam("loginFrom") @ApiParam("第三方账号类型：1. 微信，2. 阿里， 3. QQ ...") String loginFrom, @RequestParam("mobile") @ApiParam("手机号码") String mobile) {
        return toResult(sysUserContentService.unbindThirdPartAccount(loginFrom, mobile));
    }

    @RequestMapping(value = "/bindThirdPartAccount", method = RequestMethod.POST)
    @ApiOperation(value = "绑定第三方账号")
    Object bindThirdPartAccount(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        return toResult(sysUserContentService.bindThirdPartAccount(map));
    }

    @RequestMapping(value = "/testDistributeTransaction", method = RequestMethod.GET)
    @ApiOperation(value = "测试分布式事务")
    Object testDistributeTransaction(@RequestParam("id") @ApiParam("id") String id, @RequestParam("remark") @ApiParam("描述") String remark) {
        return toResult(sysUserContentService.testDistributeTransaction(id, remark));
    }
}
