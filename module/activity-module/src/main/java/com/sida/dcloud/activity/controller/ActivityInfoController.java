package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.service.ActivityInfoService;
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
@RequestMapping("activityInfo")
@Api(description = "活动信息")
public class ActivityInfoController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityInfoController.class);

    @Autowired
    private ActivityInfoService activityInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查活动列表")
    public Object list(@RequestBody @ApiParam("JSON参数") ActivityInfo param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("start_time");
            param.setOrderString("asc");
            return "";
        });
        Object object = activityInfoService.findPageList(param);
        return toResult(object);
    }

    /**
     * C端获取订单采取三段式请求
     * 订单 -> 商品 -> 商品组合
     * @param id
     * @return
     */
    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动主键id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        ActivityInfo one = activityInfoService.selectByPrimaryKey(id);
        return toResult(one);
    }

    @RequestMapping(value = "/findOneWithGoods", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动主键id获取信息（包含商品和商品组）")
    public Object findOneWithGoods(@RequestParam("id") @ApiParam("id")String id) {
        return toResult(activityInfoService.findOneWithGoods(id));
    }
    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除活动")
    public Object remove(@RequestBody @ApiParam("活动ids") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new ActivityException("没有指定要删除的活动");
        }
        activityInfoService.deleteByPrimaryKeys(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增活动")
    public Object insert(@RequestBody @ApiParam("活动信息JSON") ActivityInfo param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        activityInfoService.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新活动")
    public Object update(@RequestBody @ApiParam("活动信息JSON") ActivityInfo param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        activityInfoService.updateByPrimaryKey(param);
        return toResult();
    }

    private void checkForm(ActivityInfo param, int event) {
        checkIdEmpty(param, event);

        Optional.ofNullable(param.getName()).orElseThrow(() ->new ActivityException("活动名称不能空"));
        Optional.ofNullable(param.getStartTime()).orElseThrow(() ->new ActivityException("活动开始时间不能空"));
        Optional.ofNullable(param.getEndTime()).orElseThrow(() ->new ActivityException("活动结束时间不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
        }
    }
}