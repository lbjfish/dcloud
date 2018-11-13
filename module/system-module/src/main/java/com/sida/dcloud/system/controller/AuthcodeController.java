package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.service.AuthcodeService;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/authcodes")
@Api("短信验证码管理类")
public class AuthcodeController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(AuthcodeController.class);

    @Autowired
    private AuthcodeService authcodeService;
    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping(value = "getRemoteAuthCode", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取短信验证码")
    public Object getRemoteAuthCode(@RequestParam(value="mobile") @ApiParam("手机号码") String mobile,
                              @RequestParam(value="reqType") @ApiParam("业务类型：1-注册，2-登录") String reqType,
                              @RequestParam(value="key") @ApiParam("图形验证码key") String key,
                              @RequestParam(value="validateCode") @ApiParam("图形验证码") String validateCode) {
        //校验图形验证码
//        if (BlankUtil.isEmpty(key)) {
//            throw new ServiceException("key值不能为空！");
//        }
//        String imageCode = (String) redisUtil.get(key);
//        if (BlankUtil.isEmpty(validateCode)) {
//            throw new ServiceException("图形验证码不能为空！");
//        } else if (!validateCode.equals(imageCode)) {
//            throw new ServiceException("图形验证码不正确！");
//        } else {
//            /*验证码正确，清除*/
//            redisUtil.remove(key);
//        }
        authcodeService.getRemoteAuthCode(mobile, reqType);
        return toResult();
    }

    @RequestMapping(value = "/cleanAuthcodeRedis",method = RequestMethod.POST)
    @ResponseBody
    public void cleanAuthcodeRedis(){
        authcodeService.cleanAuthcodeRedis();
    }
}




















