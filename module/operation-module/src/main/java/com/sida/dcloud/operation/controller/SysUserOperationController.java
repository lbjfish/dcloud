package com.sida.dcloud.operation.controller;

import com.sida.dcloud.operation.common.OperationConstants;
import com.sida.dcloud.operation.common.OperationException;
import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.service.SysUserOperationService;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.MobileUtil;
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
public class SysUserOperationController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysUserOperationController.class);

    @Autowired
    private SysUserOperationService sysUserOperationService;

    @RequestMapping(value = "/generateMobileVerifyCode", method = RequestMethod.GET)
    @ApiOperation(value = "获取手机验证码")
    public Object generateMobileVerifyCode(@RequestParam("mobile") @ApiParam("手机号码")String mobile) {
        return toResult(sysUserOperationService.generateMobileVerifyCode(mobile));
    }

    @RequestMapping(value = "/verifyBindStatusByWechat", method = RequestMethod.GET)
    @ApiOperation(value = "判断微信是否绑定过手机号")
    public Object verifyBindStatusByWechat(@RequestParam("wechat") @ApiParam("微信号")String wechat) {
        return toResult(sysUserOperationService.verifyBindStatus(wechat, OperationConstants.DIC_LOGIN_FROM_WECHAT));
    }

    /********************************************************************************/

    @RequestMapping(value = "/bindMobile", method = RequestMethod.POST)
    @ApiOperation(value = "第三方登录绑定&注册用户")
    public Object bindMobile(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        return toResult(sysUserOperationService.bindMobile(param));
    }

    private void checkForm(CommonUserOperation param, int event) {
        checkIdEmpty(param, event);

        Optional.ofNullable(param.getName()).orElseThrow(() ->new OperationException("名称不能空"));
        Optional.ofNullable(param.getMobile()).orElseThrow(() ->new OperationException("手机号不能空"));
        Optional.ofNullable(param.getVerifyCode()).orElseThrow(() ->new OperationException("验证码不能空"));
        Optional.ofNullable(param.getPassword()).orElseThrow(() ->new OperationException("密码不能空"));
        if(!MobileUtil.isMobile(param.getMobile())) {
            new OperationException("手机号码格式不正确");
        }

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
        }
    }
}