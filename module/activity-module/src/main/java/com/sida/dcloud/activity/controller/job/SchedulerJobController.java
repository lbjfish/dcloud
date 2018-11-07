package com.sida.dcloud.activity.controller.job;

import com.sida.dcloud.activity.controller.CustomerActivitySignupNoteController;
import com.sida.dcloud.activity.service.ActivityOrderService;
import com.sida.dcloud.activity.service.CustomerActivitySignupNoteService;
import com.sida.dcloud.activity.service.CustomerPaymentTrackService;
import com.sida.dcloud.activity.util.pay.PayUtilWithXcx;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("schedulerJob")
@Api(description = "计划任务")
public class SchedulerJobController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerJobController.class);

    @Autowired
    private CustomerActivitySignupNoteService customerActivitySignupNoteService;
    @Autowired
    private ActivityOrderService activityOrderService;

    @RequestMapping(value = "/resendThirdPartCode", method = RequestMethod.GET)
    @ApiOperation(value = "重传所有第三方校验码")
    public Object resendThirdPartCode() {
        Object object = customerActivitySignupNoteService.resendThirdPartCode();
        return toResult(object);
    }

    @RequestMapping(value = "/scanAndChangeOrderStatus", method = RequestMethod.GET)
    @ApiOperation(value = "扫描改变订单状态（包括从腾讯支付获取支付状态）")
    public Object scanAndChangeOrderStatus() {
        Object object = activityOrderService.scanAndChangeOrderStatus();
        return toResult(object);
    }

    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.GET)
    @ApiOperation(value = "更新订单状态")
    public Object updateOrderStatus(@RequestParam("orderId") @ApiParam("订单id")String orderId,
                                    @RequestParam("orderStatus") @ApiParam("订单状态")String orderStatus) {
        Object object = activityOrderService.updateActivityOrderStatus(orderId, orderStatus);
        return toResult(object);
    }

    @RequestMapping(value = "/schedulerJob/selectUnpayOrderList", method = RequestMethod.GET)
    @ApiOperation(value = "获取未付款订单列表（未过期）")
    public Object selectUnpayOrderList() {
        return toResult(activityOrderService.selectUnpayOrderList());
    }
}
