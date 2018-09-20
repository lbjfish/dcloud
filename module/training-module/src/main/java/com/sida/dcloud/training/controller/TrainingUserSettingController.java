package com.sida.dcloud.training.controller;

import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.po.TrainingUserSetting;
import com.sida.dcloud.training.service.TrainingUserSettingService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trainingUserSetting")
@Api(description = "模块用户设置")
public class TrainingUserSettingController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TrainingUserSettingController.class);

    @Autowired
    private TrainingUserSettingService trainingUserSettingService;

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ApiOperation(value = "查找用户此模块所有配置，缓存到客户端")
    public Object load() {
        String userId = LoginManager.getCurrentUserId();
        Object object = trainingUserSettingService.selectByUserId(userId);
        return toResult(object);
    }

    /********************************************************************************/

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存设置")
    public Object save(@RequestBody @ApiParam("JSON参数") TrainingUserSetting po) {
        String userId = LoginManager.getCurrentUserId();
        if(!po.getUserId().equals(userId)) {
            throw new TrainingException("非法请求");
        }
        return toResult();
    }
}