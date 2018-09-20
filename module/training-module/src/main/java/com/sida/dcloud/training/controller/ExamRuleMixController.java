package com.sida.dcloud.training.controller;

import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.dto.ExamRuleMixDto;
import com.sida.dcloud.training.event.dispatcher.ExamRuleMixDispatcher;
import com.sida.dcloud.training.service.ExamRuleMixService;
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

import java.util.Optional;

@RestController
@RequestMapping("examRuleMix")
@Api(description = "考试规则")
public class ExamRuleMixController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExamRuleMixController.class);

    @Autowired
    private ExamRuleMixDispatcher examRuleMixDispatcher;
    @Autowired
    private ExamRuleMixService examRuleMixService;

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ApiOperation(value = "加载考试规则")
    public Object list(@RequestBody @ApiParam("所属科目") String subject) {
        Optional.ofNullable(subject).orElseThrow(() -> new TrainingException("没有指定规则科目"));
        return toResult(examRuleMixService.loadRuleBySubject(subject));
    }

    /********************************************************************************/

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新考试规则")
    public Object update(@RequestBody @ApiParam("JSON参数") ExamRuleMixDto param) {
        Optional.ofNullable(param.getSubject()).orElseThrow(() -> new TrainingException("没有指定规则科目"));
        examRuleMixDispatcher.update(param);
        return toResult();
    }
}