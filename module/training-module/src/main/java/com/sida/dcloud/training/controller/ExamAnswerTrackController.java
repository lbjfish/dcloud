package com.sida.dcloud.training.controller;

import com.sida.dcloud.training.util.TrainingCacheUtil;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.po.ExamAnswerTrack;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
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

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("examAnswerTrack")
@Api(description = "考试答题日志")
public class ExamAnswerTrackController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExamAnswerTrackController.class);

    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    @ApiOperation(value = "答题")
    public Object answer(@RequestBody @ApiParam("JSON参数") ExamAnswerTrack param) {
        checkForm(param);
        trainingCacheUtil.answerExamPaper(param);
        return toResult();
    }


    private void checkForm(ExamAnswerTrack param) {
        Optional.ofNullable(param.getId()).orElseThrow(() ->new TrainingException("必须指定考题"));
        Optional.ofNullable(param.getAnswer()).orElseThrow(() ->new TrainingException("答案不能空"));
        param.setThatTime(new Date());
        param.setUserId(LoginManager.getCurrentUserId());
    }
}