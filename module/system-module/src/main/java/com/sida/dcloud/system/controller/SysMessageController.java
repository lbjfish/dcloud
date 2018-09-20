package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.po.SysMessage;
import com.sida.dcloud.system.service.SysMessageService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sysMessage")
@Api(description = "消息管理api")
public class SysMessageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysMessageController.class);

    @Autowired
    private SysMessageService sysMessageService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户消息列表")
    public Object list(@RequestBody @ApiParam("JSON参数") SysMessage param){
        Object object = sysMessageService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增消息")
    public Object save(@RequestBody @ApiParam("JSON参数") SysMessage param) {
        sysMessageService.saveOrUpdate(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新消息")
    public Object update(@RequestBody @ApiParam("JSON参数") SysMessage param) {
        sysMessageService.saveOrUpdate(param);
        return toResult();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "逻辑删除")
    public Object delete(@RequestBody @ApiParam("JSON参数")List<String> ids) {
        sysMessageService.deleteByIds(ids);
        return toResult();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "物理删除")
    public Object remove(@RequestBody @ApiParam("JSON参数")List<String> ids) {
        sysMessageService.removeByIds(ids);
        return toResult();
    }

    @RequestMapping(value = "/removeByProcInstId", method = RequestMethod.POST)
    @ApiOperation(value = "根据流程实例id删除消息")
    public Object removeByProcInstId(@RequestParam("procInstId") String procInstId) {
        sysMessageService.removeByProcInstId(procInstId);
        return toResult();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation(value = "获取详情")
    public Object detail(@RequestParam String id) {
        if (BlankUtil.isNotEmpty(id)){
            Object object = sysMessageService.selectByPrimaryKey(id);
            return toResult(object);
        }
        return toResult();
    }

    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    @ApiOperation(value = "批量新增")
    public Object batchSave(@RequestBody @ApiParam("JSON参数") List<SysMessage> list) {
        sysMessageService.batchSave(list);
        return toResult();
    }
}