package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.po.MyUser;
import com.sida.dcloud.auth.po.SysButton;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Api(description = "测试")
public class TestController {

    @Autowired
    private RedisUtil redisUtil;

    @ResponseBody
    @RequestMapping(value = "/putUser", method = RequestMethod.GET)
    public MyUser putUser() {
        MyUser user = new MyUser();
        SysButton sysButton = new SysButton();
        sysButton.setPageCode("haha");
        user.setSysButton(sysButton);
        user.setName("张三MyUser");
        redisUtil.set("user", user);
        MyUser userInRedis = (MyUser) redisUtil.get("user");
        return userInRedis;
    }
}
