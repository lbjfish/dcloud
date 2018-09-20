package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.po.SysGlobalVariable;
import com.sida.dcloud.system.service.SysGlobalVariableService;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sysGlobalVariable")
@Api(description = "全局变量相关api")
public class SysGlobalVariableController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysGlobalVariableController.class);

    @Autowired
    private SysGlobalVariableService sysGlobalVariableService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取全局变量列表")
    public Object list(@RequestBody @ApiParam("JSON参数") SysGlobalVariable param){
        Object object = sysGlobalVariableService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新全局变量")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") SysGlobalVariable param) {
        sysGlobalVariableService.saveOrUpdate(param);
        return toResult();
    }

    @RequestMapping(value = "/enablePositions", method = RequestMethod.POST)
    @ApiOperation(value = "批量启用全局变量")
    public Object enableRoles(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        SysGlobalVariable po = new SysGlobalVariable();
        po.setDisable(false);
        sysGlobalVariableService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/disablePositions", method = RequestMethod.POST)
    @ApiOperation(value = "批量禁用全局变量")
    public Object disableRoles(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        SysGlobalVariable po = new SysGlobalVariable();
        po.setDisable(true);
        sysGlobalVariableService.updateByIdsSelective(po,ids);
        return toResult();
    }
}