package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.po.CustomerFaceDetectionTrack;
import com.sida.dcloud.activity.service.CustomerFaceDetectionTrackService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("customerFaceDetectionTrack")
@Api(description = "C端用户人脸识别日志")
public class CustomerFaceDetectionTrackController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerFaceDetectionTrackController.class);

    @Autowired
    private CustomerFaceDetectionTrackService customerFaceDetectionTrackService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查人脸识别日志列表")
    public Object list(@RequestBody @ApiParam("JSON参数") CustomerFaceDetectionTrack param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("payTime");
            param.setOrderString("desc");
            return "";
        });
        Object object = customerFaceDetectionTrackService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据人脸识别主键id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        CustomerFaceDetectionTrack one = customerFaceDetectionTrackService.selectByPrimaryKey(id);
        return toResult(one);
    }

    @RequestMapping(value = "/findListByActivityId", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动id取人脸识别日志")
    public Object findListByActivityId(@RequestParam("activityId") @ApiParam("活动id")String activityId) {
        return toResult(customerFaceDetectionTrackService.findListByActivityId(activityId));
    }

    @RequestMapping(value = "/findListByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id取人脸识别日志")
    public Object findListByUserId(@RequestParam("userId") @ApiParam("活动id")String userId) {
        return toResult(customerFaceDetectionTrackService.findListByUserId(userId));
    }

    /********************************************************************************/

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增人脸识别日志")
    public Object insert(@RequestBody @ApiParam("日志JSON") CustomerFaceDetectionTrack param) {
        param.setThatTime(new Date());
        param.setId(UUID.create().toString());
        return toResult(customerFaceDetectionTrackService.insert(param));
    }
}