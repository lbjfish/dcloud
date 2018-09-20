package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.system.po.SysTrainingfield;
import com.sida.dcloud.system.service.SysTrainingfieldService;
import com.sida.dcloud.system.vo.SysTrainingfieldVo;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.BuildKeyValueMapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sysTrainingfield")
@Api(description = "训练场相关接口")
public class SysTrainingfieldController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysTrainingfieldController.class);

    @Autowired
    private SysTrainingfieldService sysTrainingfieldService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取训练场列表")
    public Object list(@RequestBody @ApiParam("JSON参数") SysTrainingfieldVo param) {
        Object object = sysTrainingfieldService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新训练场")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") SysTrainingfieldVo param) {
        sysTrainingfieldService.save(param);
        return toResult();
    }

    @RequestMapping(value = "/getInfo/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取训练场详情")
    public Object getInfo(@PathVariable("id") String id) {
        Object object = sysTrainingfieldService.selectVoByPrimaryKey(id);
        return toResult(object);
    }

    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    @ApiOperation(value = "批量启用训练场")
    public Object enableRoles(@RequestBody @ApiParam("JSON参数")List<String> ids) {
        SysTrainingfield po = new SysTrainingfield();
        po.setDisable(false);
        sysTrainingfieldService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ApiOperation(value = "批量禁用训练场")
    public Object disableRoles(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        SysTrainingfield po = new SysTrainingfield();
        po.setDisable(true);
        sysTrainingfieldService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/selectList", method = RequestMethod.POST)
    @ApiOperation(value = "获取训练场候选列表")
    public Object selectList(@RequestBody @ApiParam("JSON参数") String areaId) {
        SysTrainingfield condition = new SysTrainingfield();
        condition.setDisable(false);
        condition.setDeleteFlag(false);
        condition.setOrgId(LoginManager.getCurrentOrgId());
        if (BlankUtil.isNotEmpty(areaId)){
            condition.setAreaId(areaId);
        }
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysTrainingfield> list = sysTrainingfieldService.selectByCondition(condition);
        //封装list为map，减少多余的字段
        return toResult(BuildKeyValueMapUtil.change(list));
    }
}