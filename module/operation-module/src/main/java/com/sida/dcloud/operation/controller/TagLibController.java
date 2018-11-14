package com.sida.dcloud.operation.controller;

import com.sida.dcloud.operation.po.TagLib;
import com.sida.dcloud.operation.service.TagLibService;
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
import scala.annotation.meta.param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("tagLib")
@Api(description = "标签信息")
public class TagLibController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(TagLibController.class);

    @Autowired
    private TagLibService tagLibService;
    @Autowired
    private OperationRedisUtil operationRedisUtil;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查标签库列表 - 分页")
    public Object list(@RequestBody @ApiParam("JSON参数") TagLib param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = tagLibService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ApiOperation(value = "条件查标签库列表 - 全部")
    public Object all(@RequestBody @ApiParam("JSON参数") TagLib param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = tagLibService.findAllList(param);
        return toResult(object);
    }

    /**
     * 智能匹配
     * @param param
     * @return
     */
    @RequestMapping(value = "/match", method = RequestMethod.POST)
    @ApiOperation(value = "根据传入的标签智能匹配")
    public Object match(@RequestBody @ApiParam("JSON参数") Map<String, String> param) {
        Object object = operationRedisUtil.intelligentMatch(param);
        return toResult(object);
    }
}