package com.sida.dcloud.operation.controller;

import com.sida.dcloud.operation.common.OperationException;
import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.CommonUser;
import com.sida.dcloud.operation.service.CommonUserService;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scala.annotation.meta.param;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("commonUser")
@Api(description = "普通用户信息")
public class CommonUserController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(CommonUserController.class);

    @Autowired
    private CommonUserService commonUserService;

    /**
     * 根据id获取用户信息 - 提供auth服务使用
     * @param userId
     * @return
             */
    @RequestMapping(value = "/findCommonUserById", method = RequestMethod.GET)
    @ApiOperation(value = "根据主键id获取部分信息")
    public Object findCommonUserById(@RequestParam("userId") @ApiParam("用户id")String userId) {
        Map<String, String> one = commonUserService.selectByPrimaryKeyToAuth(userId);
        return toResult(one);
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据主键id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        CommonUser one = commonUserService.selectByPrimaryKey(id);
        return toResult(one);
    }
    /********************************************************************************/

    @RequestMapping(value = "/updateFaceUrl", method = RequestMethod.POST)
    @ApiOperation(value = "更新人脸图片地址")
    public Object updateFaceUrl(@RequestBody @ApiParam("用户信息JSON") Map<String, String> map) {
        Optional.ofNullable(map.get("id")).orElseThrow(() -> new OperationException("id不能空"));
        Optional.ofNullable(map.get("faceUrl")).orElseThrow(() -> new OperationException("人脸图片url不能空"));
        return toResult(commonUserService.updateFaceUrl(map));
    }
}