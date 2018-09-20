package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysPosition;
import com.sida.dcloud.system.service.SysPositionService;
import com.sida.dcloud.auth.vo.EmpListConditionDTO;
import com.sida.xiruo.xframework.controller.BaseController;
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
@RequestMapping("sysPosition")
@Api(description = "岗位管理api")
public class SysPositionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysPositionController.class);

    @Autowired
    private SysPositionService sysPositionService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取岗位列表")
    public Object list(@RequestBody @ApiParam("JSON参数") SysPosition param){
        Object object = sysPositionService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新岗位")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") SysPosition param) {
        sysPositionService.savePosition(param);
        return toResult();
    }

    @RequestMapping(value = "/enablePositions", method = RequestMethod.POST)
    @ApiOperation(value = "批量启用岗位")
    public Object enableRoles(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        SysPosition po = new SysPosition();
        po.setDisable(false);
        sysPositionService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/disablePositions", method = RequestMethod.POST)
    @ApiOperation(value = "批量禁用岗位")
    public Object disableRoles(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        SysPosition po = new SysPosition();
        po.setDisable(true);
        sysPositionService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/selectList", method = RequestMethod.POST)
    @ApiOperation(value = "职位|岗位下拉框候选值列表")
    public Object selectList() {
        SysPosition condition = new SysPosition();
        condition.setDisable(false);
        condition.setDeleteFlag(false);
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysPosition> positionList = sysPositionService.selectByCondition(condition);
        //封装list为map，减少多余的字段
        return toResult(BuildKeyValueMapUtil.change(positionList));
    }

    @RequestMapping(value = "/empList", method = RequestMethod.POST)
    @ApiOperation(value = "根据岗位id获取员工列表")
    public Object empList(@RequestBody @ApiParam("JSON参数")EmpListConditionDTO param) {
        Object object = sysPositionService.findEmpListByPositionId(param);
        return toResult(object);
    }


}