package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.po.ActivitySignupNoteVersion;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.service.ActivitySignupNoteVersionService;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("activitySignupNoteVersion")
@Api(description = "活动报名表版本")
public class ActivitySignupNoteVersionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ActivitySignupNoteVersionController.class);

    @Autowired
    private ActivitySignupNoteVersionService activitySignupNoteVersionService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查报名设置版本列表")
    public Object list(@RequestBody @ApiParam("JSON参数") ActivitySignupNoteVersion param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("versionTime");
            param.setOrderString("desc");
            return "";
        });
        Object object = activitySignupNoteVersionService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据条件查报名设置版本获取信息")
    public Object findOne(@RequestParam("version") @ApiParam("version")String version) {
        ActivitySignupNoteVersion one = activitySignupNoteVersionService.selectByVerion(version);
        return toResult(one);
    }

    @RequestMapping(value = "/changeCurrentVersion", method = RequestMethod.GET)
    @ApiOperation(value = "改变当前报名设置版本")
    public Object changeCurrentVersion(@RequestParam("version") @ApiParam("version")String version) {
        activitySignupNoteVersionService.changeCurrentVersion(version);
        return toResult();
    }
}