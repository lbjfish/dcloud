package com.sida.dcloud.training.controller;

import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.dispatcher.LightingSimulatorDispatcher;
import com.sida.dcloud.training.po.LightingSimulator;
import com.sida.dcloud.training.service.LightingSimulatorService;
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
@RequestMapping("lightingSimulator")
@Api(description = "灯光模拟")
public class LightingSimulatorController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LightingSimulatorController.class);

    @Autowired
    private LightingSimulatorDispatcher lightingSimulatorDispatcher;
    @Autowired
    private LightingSimulatorService lightingSimulatorService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查灯光模拟信息列表")
    public Object list(@RequestBody @ApiParam("JSON参数") LightingSimulator param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = lightingSimulatorService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "根据灯光模拟信息主键id获取信息")
    public Object findOne(@RequestBody @ApiParam("id")String id) {
        LightingSimulator one = lightingSimulatorService.selectByPrimaryKey(id);
        return toResult(one);
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除灯光模拟信息")
    public Object remove(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new TrainingException("没有指定要删除的项目");
        }
        lightingSimulatorDispatcher.remove(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增灯光模拟信息")
    public Object insert(@RequestBody @ApiParam("JSON参数") LightingSimulator param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        lightingSimulatorDispatcher.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新灯光模拟信息")
    public Object update(@RequestBody @ApiParam("JSON参数") LightingSimulator param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        lightingSimulatorDispatcher.update(param);
        return toResult();
    }

    private void checkForm(LightingSimulator param, int event) {
        String id = Optional.ofNullable(param.getId()).orElse("");
        if(EventConstants.EVENT_UPDATE == event && "".equals(id)) {
            throw new TrainingException("更新操作时主键不能空");
        }

        Optional.ofNullable(param.getName()).orElseThrow(() ->new TrainingException("名字不能空"));
        Optional.ofNullable(param.getCode()).orElseThrow(() ->new TrainingException("编码不能空"));
        Optional.ofNullable(param.getAudioUrl()).orElseThrow(() ->new TrainingException("语音url地址不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
        }
    }
}