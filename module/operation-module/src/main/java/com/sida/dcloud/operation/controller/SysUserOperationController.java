package com.sida.dcloud.operation.controller;

import com.sida.dcloud.operation.common.OperationConstants;
import com.sida.dcloud.operation.common.OperationException;
import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.SysUserOperation;
import com.sida.dcloud.operation.service.SysUserOperationService;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.MobileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("sysUserOperation")
@Api(description = "系统用户信息")
public class SysUserOperationController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysUserOperationController.class);

    @Autowired
    private SysUserOperationService sysUserOperationService;

    @RequestMapping(value = "/generateMobileVerifyCode", method = RequestMethod.GET)
    @ApiOperation(value = "获取手机验证码")
    public Object generateMobileVerifyCode(@RequestParam("mobileType") @ApiParam("终端类型")String mobileType,
            @RequestParam("mobile") @ApiParam("手机号码")String mobile) {
        return toResult(sysUserOperationService.generateMobileVerifyCode(mobileType, mobile));
    }

    @RequestMapping(value = "/verifyBindStatus", method = RequestMethod.GET)
    @ApiOperation(value = "判断第三方账号是否绑定过手机号")
    public Object verifyBindStatus(@RequestParam("dto") @ApiParam("json数据") CommonUserOperation dto) {
        return toResult(sysUserOperationService.verifyBindStatus(dto));
    }

    /********************************************************************************/

    @RequestMapping(value = "/bindMobile", method = RequestMethod.POST)
    @ApiOperation(value = "登录绑定&注册用户")
    public Object bindMobile(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        return toResult(sysUserOperationService.bindMobile(param));
    }

    @RequestMapping(value = "/bindNewMobile", method = RequestMethod.POST)
    @ApiOperation(value = "更改手机")
    public Object bindNewMobile(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        Optional.ofNullable(param.getId()).orElseThrow(() ->new OperationException("id不能空"));
        Optional.ofNullable(param.getMobile()).orElseThrow(() ->new OperationException("手机号不能空"));
        Optional.ofNullable(param.getVerifyCode()).orElseThrow(() ->new OperationException("验证码不能空"));
        Optional.ofNullable(param.getMobileType()).orElseThrow(() ->new OperationException("终端类型不能空"));
        return toResult(sysUserOperationService.bindNewMobile(param));
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户资料")
    public Object updateUserInfo(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        return toResult(sysUserOperationService.updateUserInfo(param));
    }

    @RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户密码")
    public Object updateUserPassword(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        Optional.ofNullable(param.getId()).orElseThrow(() -> new RuntimeException("id不能空"));
        Optional.ofNullable(param.getPassword()).orElseThrow(() ->new OperationException("密码不能空"));
        return toResult(sysUserOperationService.updateUserPassword(param));
    }

    private void checkForm(CommonUserOperation param, int event) {
        checkIdEmpty(param, event);

        Optional.ofNullable(param.getName()).orElseThrow(() ->new OperationException("名称不能空"));
        if(EventConstants.EVENT_INSERT == event) {
//            Optional.ofNullable(param.getPassword()).orElseThrow(() ->new OperationException("密码不能空"));
            Optional.ofNullable(param.getMobile()).orElseThrow(() ->new OperationException("手机号不能空"));
            Optional.ofNullable(param.getVerifyCode()).orElseThrow(() ->new OperationException("验证码不能空"));
            Optional.ofNullable(param.getMobileType()).orElseThrow(() ->new OperationException("终端类型不能空"));
            if(!MobileUtil.isMobile(param.getMobile())) {
                new OperationException("手机号码格式不正确");
            }
            param.setSort(0);
            param.setName(param.getMobile());
            param.setPassword(param.getMobile());
        }
        fillDefaultFields(param, event);
    }
}