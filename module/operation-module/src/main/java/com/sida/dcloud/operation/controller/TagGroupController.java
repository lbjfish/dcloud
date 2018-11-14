package com.sida.dcloud.operation.controller;

import com.sida.dcloud.operation.po.TagGroup;
import com.sida.dcloud.operation.service.TagGroupService;
import com.sida.dcloud.operation.util.OperationRedisUtil;
import com.sida.xiruo.xframework.controller.BaseController;
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

import java.util.Optional;

@RestController
@RequestMapping("tagGroup")
@Api(description = "标签纬度")
public class TagGroupController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(TagGroupController.class);

    @Autowired
    private TagGroupService tagGroupService;
    @Autowired
    private OperationRedisUtil operationRedisUtil;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查标签组列表 - 分页")
    public Object list(@RequestBody @ApiParam("JSON参数") TagGroup param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = tagGroupService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "条件查标签组列表 - 全部")
    public Object all() {
//        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
//            param.setOrderField("sort");
//            param.setOrderString("asc");
//            return "";
//        });
//        Object object = tagGroupService.findAllList(param);
        return toResult(operationRedisUtil.loadTagInfoInRedis());
    }
}