package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.service.ActivityOrderService;
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
@RequestMapping("activityOrder")
@Api(description = "活动订单")
public class ActivityOrderController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityOrderController.class);

    @Autowired
    private ActivityOrderService activityOrderService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查活动订单列表")
    public Object list(@RequestBody @ApiParam("JSON参数") ActivityOrder param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("createTime");
            param.setOrderString("desc");
            return "";
        });
        Object object = activityOrderService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动订单主键id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        ActivityOrder one = activityOrderService.selectByPrimaryKey(id);
        return toResult(one);
    }
    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除活动订单")
    public Object remove(@RequestBody @ApiParam("活动订单ids") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new ActivityException("没有指定要删除的活动订单");
        }
        activityOrderService.deleteByPrimaryKeys(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增活动订单")
    public Object insert(@RequestBody @ApiParam("活动订单信息JSON") ActivityOrder param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        activityOrderService.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新活动订单")
    public Object update(@RequestBody @ApiParam("活动订单信息JSON") ActivityOrder param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        activityOrderService.updateByPrimaryKey(param);
        return toResult();
    }

    private void checkForm(ActivityOrder param, int event) {
        String id = Optional.ofNullable(param.getId()).orElse("");
        if(EventConstants.EVENT_UPDATE == event && "".equals(id)) {
            throw new ActivityException("更新操作时主键不能空");
        }

        Optional.ofNullable(param.getOrderName()).orElseThrow(() ->new ActivityException("订单名称不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
            param.setCreateTime(param.getCreatedAt());
        }
    }
}