package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.service.ActivitySignupNoteSettingService;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("activitySignupNoteSetting")
@Api(description = "活动报名表设置")
public class ActivitySignupNoteSettingController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitySignupNoteSettingController.class);

    @Autowired
    private ActivitySignupNoteSettingService activitySignupNoteSettingService;

    @RequestMapping(value="load", method = RequestMethod.GET)
    @ApiOperation("根据版本加载设置数据")
    public Object load(@ApiParam("版本") @RequestParam(value="version") String version) {
        LOG.info("Version is {}", version);
        Optional.ofNullable(version).orElseThrow(() -> new ActivitiException("必须指定加载的版本"));
        return toResult(activitySignupNoteSettingService.selectByVersion(version));
    }

    @RequestMapping(value="generate", method = RequestMethod.GET)
    @ApiOperation("根据版本产生设置数据")
    public Object generate(@ApiParam("版本") @RequestParam(value="version") String version) {
        version = Optional.ofNullable(version).orElse(Xiruo.dateToString(new Date(), "yyyyMMdd"));
        LOG.info("Version is {}", version);
        return toResult(activitySignupNoteSettingService.generateSetting(version));
    }

    @RequestMapping(value="delete", method = RequestMethod.GET)
    @ApiOperation("根据版本逻辑删除设置数据")
    public Object delete(@ApiParam("版本") @RequestParam(value="version") String version) {
        LOG.info("Version is {}", version);
        Optional.ofNullable(version).orElseThrow(() -> new ActivitiException("必须指定加载的版本"));
        return toResult(activitySignupNoteSettingService.deleteByVersion(version));
    }

    @RequestMapping(value="resume", method = RequestMethod.GET)
    @ApiOperation("根据版本逻辑恢复设置数据")
    public Object resume(@ApiParam("版本") @RequestParam(value="version") String version) {
        LOG.info("Version is {}", version);
        Optional.ofNullable(version).orElseThrow(() -> new ActivitiException("必须指定加载的版本"));
        return toResult(activitySignupNoteSettingService.resumeByVersion(version));
    }

    @RequestMapping(value="update", method = RequestMethod.POST)
    @ApiOperation("根据版本更新设置数据")
    public Object update(@RequestBody @ApiParam("设置项集合") List<ActivitySignupNoteSetting> settingList) {
        if(settingList.isEmpty()) throw new ActivitiException("设置列表不能空");
        return toResult(activitySignupNoteSettingService.updateSettingList(settingList));
    }
}