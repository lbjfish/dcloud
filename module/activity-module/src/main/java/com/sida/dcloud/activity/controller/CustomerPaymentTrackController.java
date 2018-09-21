package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.service.CustomerPaymentTrackService;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customerPaymentTrack")
@Api(description = "C端用户支付日志")
public class CustomerPaymentTrackController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerPaymentTrackController.class);

    @Autowired
    private CustomerPaymentTrackService customerPaymentTrackService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查支付日志列表")
    public Object list(@RequestBody @ApiParam("JSON参数") CustomerPaymentTrack param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("payTime");
            param.setOrderString("desc");
            return "";
        });
        Object object = customerPaymentTrackService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据支付主键id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        CustomerPaymentTrack one = customerPaymentTrackService.selectByPrimaryKey(id);
        return toResult(one);
    }

    @RequestMapping(value = "/findListByActivityId", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动id取支付日志")
    public Object findListByActivityId(@RequestParam("activityId") @ApiParam("活动id")String activityId) {
        return toResult(customerPaymentTrackService.findListByActivityId(activityId));
    }

    @RequestMapping(value = "/findListByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id取支付日志")
    public Object findListByUserId(@RequestParam("userId") @ApiParam("活动id")String userId) {
        return toResult(customerPaymentTrackService.findListByUserId(userId));
    }

    /********************************************************************************/

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增支付日志")
    public Object insert(@RequestBody @ApiParam("日志JSON") CustomerPaymentTrack param) {
        param.setPayTime(new Date());
        return toResult(customerPaymentTrackService.insert(param));
    }
}