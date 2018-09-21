package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.ActivityRelHonored;
import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.service.ActivityRelHonoredService;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("activityRelHonored")
@Api(description = "活动安排与嘉宾关联表")
public class ActivityRelHonoredController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityRelHonoredController.class);

    @Autowired
    private ActivityRelHonoredService activityRelHonoredService;

    @RequestMapping(value = "/removeByScheduleId", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动安排删除嘉宾")
    public Object remove(@RequestParam("scheduleId") @ApiParam("活动安排id") String scheduleId) {
        return toResult(activityRelHonoredService.deleteByScheduleId(scheduleId));
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "根据活动安排更新嘉宾")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON集合") List<ActivityRelHonored> poList) {
        return toResult(activityRelHonoredService.saveOrUpdate(poList));
    }
}