package com.sida.dcloud.training.controller;

import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.dispatcher.SpecialGroupDispatcher;
import com.sida.dcloud.training.po.SpecialGroup;
import com.sida.dcloud.training.service.SpecialGroupService;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("specialGroup")
@Api(description = "专项分组")
public class SpecialGroupController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SpecialGroupController.class);

    @Autowired
    private SpecialGroupDispatcher specialGroupDispatcher;
    @Autowired
    private SpecialGroupService specialGroupService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查习题分组列表")
    public Object list(@RequestBody @ApiParam("JSON参数") SpecialGroup param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = specialGroupService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "根据习题分组主键id获取信息")
    public Object findOne(@RequestBody @ApiParam("id")String id) {
        SpecialGroup one = specialGroupService.selectByPrimaryKey(id);
        return toResult(one);
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除习题分组")
    public Object remove(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new TrainingException("没有指定要删除的项目");
        }
        specialGroupDispatcher.remove(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增习题分组")
    public Object insert(@RequestBody @ApiParam("JSON参数") SpecialGroup param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        specialGroupDispatcher.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新习题分组")
    public Object update(@RequestBody @ApiParam("JSON参数") SpecialGroup param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        specialGroupDispatcher.update(param);
        return toResult();
    }

    private void checkForm(SpecialGroup param, int event) {
        if(EventConstants.EVENT_UPDATE == event && !Optional.ofNullable(param.getId()).isPresent()) {
            throw new TrainingException("更新操作时主键不能空");
        }

        Optional.ofNullable(param.getName()).orElseThrow(() -> new TrainingException("名字不能空"));
        Optional.ofNullable(param.getCode()).orElseThrow(() -> new TrainingException("编码不能空"));
        Optional.ofNullable(param.getSubject()).orElseThrow(() -> new TrainingException("所属科目不能空"));
        Optional.ofNullable(param.getUrl()).orElseThrow(() -> new TrainingException("图标地址不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
            param.setTotal(0);
        }
    }
}