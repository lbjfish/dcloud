package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.ConsultationInfo;
import com.sida.dcloud.activity.service.ConsultationInfoService;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("consultationInfo")
public class ConsultationInfoController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ConsultationInfoController.class);

    @Autowired
    private ConsultationInfoService consultationInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查询对接信息列表")
    public Object list(@RequestBody @ApiParam("JSON参数") ConsultationInfo param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("con_time");
            param.setOrderString("asc");
            return "";
        });
        Object object = consultationInfoService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取对接信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        ConsultationInfo one = consultationInfoService.selectByPrimaryKey(id);
        return toResult(one);
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除对接信息")
    public Object remove(@RequestBody @ApiParam("对接信息ids") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new ActivityException("没有指定要删除的对接信息");
        }
        consultationInfoService.deleteByPrimaryKeys(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增对接信息")
    public Object insert(@RequestBody @ApiParam("对接信息信息JSON") ConsultationInfo param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        return toResult(consultationInfoService.insert(param));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新对接信息")
    public Object update(@RequestBody @ApiParam("对接信息信息JSON") ConsultationInfo param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        return toResult(consultationInfoService.updateByPrimaryKey(param));
    }

    private void checkForm(ConsultationInfo param, int event) {
        checkIdEmpty(param, event);

        Optional.ofNullable(param.getCompanyId()).orElseThrow(() ->new ActivityException("对接企业id不能空"));
        Optional.ofNullable(param.getNoteId()).orElseThrow(() ->new ActivityException("报名表id不能空"));

        fillDefaultFields(param, event);
        param.setUserId(param.getCreatedUser());
    }
}