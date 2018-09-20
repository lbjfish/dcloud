package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.po.SysDictionaryData;
import com.sida.dcloud.system.service.SysDictionaryDataService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.BlankUtil;
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
import java.util.Map;

/**
 * 数据字典数据表（性别[男，女]）
 * created by huangbaidong
 * 2017-10-26
 */
@RestController
@RequestMapping("sysDictionaryData")
@Api(description = "数据字典值表")
public class SysDictionaryDataController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryDataController.class);

    @Autowired
    private SysDictionaryDataService sysDictionaryDataService;

    @ResponseBody
    @ApiOperation("字典列表")
    @PostMapping("/list")
    public Object list(@RequestBody SysDictionaryData param) {
        Page<SysDictionaryData> pageList = sysDictionaryDataService.findPageList(param);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation("查询所有字典")
    @PostMapping("/findAll")
    public Object findAllForCache() {
        Map<String, Map<String, String>> dicts = sysDictionaryDataService.findAllForCache();
        return toResult(dicts);
    }

    @ResponseBody
    @ApiOperation(value = "新增",response = SysDictionaryData.class)
    @PostMapping("/insert")
    public Object insert(@RequestBody SysDictionaryData param) {
        PoDefaultValueGenerator.putCreateDefault(param, LoginManager.getUser());
        checkForm(param);
        sysDictionaryDataService.addData(param);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "编辑",response = SysDictionaryData.class)
    @PostMapping("/update")
    public Object update(@RequestBody SysDictionaryData param) {
        PoDefaultValueGenerator.putUpdateDefault(param, LoginManager.getUser());
        checkForm(param);
        sysDictionaryDataService.update(param);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "删除",response = SysDictionaryData.class)
    @PostMapping("/delete")
    public Object delete(@RequestBody List<String> ids) {
        if(BlankUtil.isNotEmpty(ids)) {
            sysDictionaryDataService.delete(ids);
        }
        return toResult();
    }



    /**
     * 校验Form表单
     * @param param
     */
    private void checkForm(SysDictionaryData param) {
        FormCheckUtil.checkRequiredField(param.getGroupId(), "所属分组");
        FormCheckUtil.checkRequiredField(param.getDicCode(), "字典编码");
        FormCheckUtil.checkRequiredField(param.getDicName(), "字典名称");
    }
}