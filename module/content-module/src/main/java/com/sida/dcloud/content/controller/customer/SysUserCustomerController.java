package com.sida.dcloud.content.controller.customer;

import com.sida.dcloud.content.service.SysUserContentService;
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
    private SysUserContentService sysUserContentService;

    @RequestMapping(value = "/inertDto", method = RequestMethod.POST)
    @ApiOperation(value = "同步用户数据到系统用户表，其他服务调用")
    public Object inertDto(@RequestBody @ApiParam("JSON参数") Map<String, String> map) {
        //CommonUserOperation
        return toResult(sysUserContentService.inertDto(map));
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
}
