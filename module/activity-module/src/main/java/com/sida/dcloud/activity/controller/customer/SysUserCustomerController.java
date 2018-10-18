package com.sida.dcloud.activity.controller.customer;

import com.sida.dcloud.activity.service.SysUserActivityService;
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
    private SysUserActivityService sysUserActivityService;

    @RequestMapping(value = "/inertDto", method = RequestMethod.POST)
    @ApiOperation(value = "同步用户数据到系统用户表，其他服务调用")
    public Object inertDto(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        //CommonUserOperation
        return toResult(sysUserActivityService.inertDto(map));
    }

    @RequestMapping(value = "/updateMobile", method = RequestMethod.POST)
    @ApiOperation(value = "修改新手机号，其他服务调用")
    public Object updateMobile(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        return toResult(sysUserActivityService.updateMobile(map));
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户资料")
    public Object updateUserInfo(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        return toResult(sysUserActivityService.updateUserInfo(map));
    }
}
