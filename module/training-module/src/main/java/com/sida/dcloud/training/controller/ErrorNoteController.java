package com.sida.dcloud.training.controller;

import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.util.TrainingCacheUtil;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.dispatcher.ErrorNoteDispatcher;
import com.sida.dcloud.training.po.ErrorNote;
import com.sida.dcloud.training.po.Exercise;
import com.sida.dcloud.training.service.ErrorNoteService;
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
@RequestMapping("errorNote")
@Api(description = "错题日志")
public class ErrorNoteController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorNoteController.class);

    @Autowired
    private ErrorNoteService errorNoteService;
    @Autowired
    private ErrorNoteDispatcher errorNoteDispatcher;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ApiOperation(value = "所有错题，c端调用")
    public Object all() {
        ErrorNote po = new ErrorNote();
        po.setUserId(LoginManager.getCurrentUserId());
        Object object = errorNoteService.selectByUserWithCorrected(po);
        return toResult(object);
    }

    /********************************************************************************/
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "练习答题错误，c端调用，建议前端判断答题错误时请求")
    public Object create(@RequestBody @ApiParam("JSON参数") ErrorNote param) {
        Optional.ofNullable(param.getExerciseId()).orElseThrow(() ->new TrainingException("习题号不能空"));
        Optional.ofNullable(param.getAnswer()).orElseThrow(() ->new TrainingException("答案不能空"));
        Exercise exercise = trainingCacheUtil.getExerciseById(param.getId());
        //错题
        if(!param.getAnswer().equals(exercise.getAnswer())) {
            param.setRightAnswer(exercise.getAnswer());
            param.setUserId(LoginManager.getCurrentUserId());
            param.setErrorTime(new Date());
            errorNoteDispatcher.insert(param);
        }
        return toResult();
    }

    @RequestMapping(value = "/correct", method = RequestMethod.POST)
    @ApiOperation(value = "纠错，c端调用")
    public Object correct(@RequestBody @ApiParam("JSON参数") ErrorNote param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        errorNoteDispatcher.update(param);
        return toResult();
    }

    private void checkForm(ErrorNote param, int event) {
        if(EventConstants.EVENT_UPDATE == event && !Optional.ofNullable(param.getId()).isPresent()) {
            throw new TrainingException("更新操作时主键不能空");
        }

        Optional.ofNullable(param.getAnswer()).orElseThrow(() -> new TrainingException("答案不能空"));
    }
}