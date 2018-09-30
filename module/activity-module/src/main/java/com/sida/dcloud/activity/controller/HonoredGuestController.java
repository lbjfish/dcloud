package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.service.HonoredGuestService;
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
@RequestMapping("honoredGuest")
@Api(description = "嘉宾信息")
public class HonoredGuestController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(HonoredGuestController.class);

    @Autowired
    private HonoredGuestService honoredGuestService;


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查嘉宾列表")
    public Object list(@RequestBody @ApiParam("JSON参数") HonoredGuest param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = honoredGuestService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据嘉宾主键id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        HonoredGuest one = honoredGuestService.selectByPrimaryKey(id);
        return toResult(one);
    }

    @RequestMapping(value = "/findListByScheduleId", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动安排id取嘉宾")
    public Object findListByScheduleId(@RequestParam("scheduleId") @ApiParam("活动安排id")String scheduleId) {
        return toResult(honoredGuestService.findVoListByScheduleId(scheduleId));
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除嘉宾")
    public Object remove(@RequestBody @ApiParam("嘉宾ids") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new ActivityException("没有指定要删除的嘉宾");
        }
        honoredGuestService.deleteByPrimaryKeys(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增嘉宾")
    public Object insert(@RequestBody @ApiParam("嘉宾信息JSON") HonoredGuest param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        honoredGuestService.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新嘉宾")
    public Object update(@RequestBody @ApiParam("嘉宾信息JSON") HonoredGuest param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        honoredGuestService.updateByPrimaryKey(param);
        return toResult();
    }

    private void checkForm(HonoredGuest param, int event) {
        checkIdEmpty(param, event);

        Optional.ofNullable(param.getName()).orElseThrow(() ->new ActivityException("名字不能空"));
        Optional.ofNullable(param.getHonor()).orElseThrow(() ->new ActivityException("头衔不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
        }
    }
}