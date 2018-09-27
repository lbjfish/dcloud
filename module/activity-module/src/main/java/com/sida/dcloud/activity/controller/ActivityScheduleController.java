package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.ActivitySchedule;
import com.sida.dcloud.activity.service.ActivityScheduleService;
import com.sida.dcloud.activity.service.ActivityScheduleService;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("activitySchedule")
@Api(description = "活动安排")
public class ActivityScheduleController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityScheduleController.class);

    @Autowired
    private ActivityScheduleService activityScheduleService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查活动安排列表")
    public Object list(@RequestBody @ApiParam("JSON参数") ActivitySchedule param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("start_time");
            param.setOrderString("desc");
            return "";
        });
        Object object = activityScheduleService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动安排主键id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        ActivitySchedule one = activityScheduleService.selectByPrimaryKey(id);
        return toResult(one);
    }

    @RequestMapping(value = "/findListByHonoredGuestId", method = RequestMethod.GET)
    @ApiOperation(value = "根据嘉宾id取活动安排")
    public Object findListByHonoredGuestId(@RequestParam("honoredGuestId") @ApiParam("嘉宾id")String honoredGuestId) {
        return toResult(activityScheduleService.findVoListByHonoredGuestId(honoredGuestId));
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除活动安排")
    public Object remove(@RequestBody @ApiParam("活动安排ids") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new ActivityException("没有指定要删除的活动安排");
        }
        activityScheduleService.deleteByPrimaryKeys(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增活动安排")
    public Object insert(@RequestBody @ApiParam("活动安排信息JSON") ActivitySchedule param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        activityScheduleService.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新活动安排")
    public Object update(@RequestBody @ApiParam("活动安排信息JSON") ActivitySchedule param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        activityScheduleService.updateByPrimaryKey(param);
        return toResult();
    }

    private void checkForm(ActivitySchedule param, int event) {
        String id = Optional.ofNullable(param.getId()).orElse("");
        if(EventConstants.EVENT_UPDATE == event && "".equals(id)) {
            throw new ActivityException("更新操作时主键不能空");
        }

        Optional.ofNullable(param.getActivityId()).orElseThrow(() ->new ActivityException("活动不能空"));
        Optional.ofNullable(param.getStartTime()).orElseThrow(() ->new ActivityException("开始时间不能空"));
        Optional.ofNullable(param.getEndTime()).orElseThrow(() ->new ActivityException("结束时间不能空"));
        Optional.ofNullable(param.getSubject()).orElseThrow(() ->new ActivityException("主题不能空"));
        Optional.ofNullable(param.getLocation()).orElseThrow(() ->new ActivityException("位置不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
        }
    }
}