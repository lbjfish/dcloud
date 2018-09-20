package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.po.SysDictionaryGroup;
import com.sida.dcloud.system.service.SysDictionaryGroupService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.FormCheckUtil;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典分组表（性别，状态，类型，品种等大类）
 * created by huangbaidong
 * 2017-10-26
 */
@RestController
@RequestMapping("sysDictionaryGroup")
@Api(description = "数据字典分组表")
public class SysDictionaryGroupController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryGroupController.class);

    @Autowired
    private SysDictionaryGroupService sysDictionaryGroupService;

    @ResponseBody
    @ApiOperation("字典分组列表")
    @PostMapping("/list")
    public Object list(@RequestBody SysDictionaryGroup param) {
        Page<SysDictionaryGroup> pageList = sysDictionaryGroupService.findPageList(param);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "新增",response = SysDictionaryGroup.class)
    @PostMapping("/insert")
    public Object insert(@RequestBody SysDictionaryGroup param) {
        PoDefaultValueGenerator.putCreateDefault(param, LoginManager.getUser());
        checkForm(param);
        sysDictionaryGroupService.addGroup(param);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "编辑",response = SysDictionaryGroup.class)
    @PostMapping("/update")
    public Object update(@RequestBody  SysDictionaryGroup param) {
        PoDefaultValueGenerator.putUpdateDefault(param, LoginManager.getUser());
        checkForm(param);
        sysDictionaryGroupService.update(param);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "删除",response = SysDictionaryGroup.class)
    @PostMapping("/delete")
    public Object delete(@RequestBody List<String> ids) {
        sysDictionaryGroupService.delete(ids);
        return toResult();
    }

    /**
     * 校验Form表单
     * @param param
     */
    private void checkForm(SysDictionaryGroup param) {
        FormCheckUtil.checkRequiredField(param.getGroupCode(), "分组编号");
        FormCheckUtil.checkRequiredField(param.getGroupName(), "分组名称");
    }
}