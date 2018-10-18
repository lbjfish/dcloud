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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
