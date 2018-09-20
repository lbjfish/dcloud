package com.sida.dcloud.training.controller;

import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.dispatcher.ExamTrackDispatcher;
import com.sida.dcloud.training.po.ExamTrack;
import com.sida.dcloud.training.service.ExamTrackService;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("examTrack")
@Api(description = "模拟考试日志")
public class ExamTrackController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExamTrackController.class);

    @Autowired
    private ExamTrackDispatcher examTrackDispatcher;
    @Autowired
    private ExamTrackService examTrackService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查模拟考试日志列表")
    public Object list(@RequestBody @ApiParam("JSON参数") ExamTrack param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = examTrackService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "根据模拟考试日志主键id获取信息")
    public Object findOne(@RequestBody @ApiParam("id")String id) {
        ExamTrack one = examTrackService.selectByPrimaryKey(id);
        return toResult(one);
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除模拟考试日志")
    public Object remove(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new TrainingException("没有指定要删除的项目");
        }
        examTrackDispatcher.remove(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ApiOperation(value = "开始模拟考试")
    public Object start(@RequestBody @ApiParam("JSON参数") ExamTrack param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        examTrackDispatcher.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/end", method = RequestMethod.POST)
    @ApiOperation(value = "结束模拟考试")
    public Object end(@RequestBody @ApiParam("JSON参数") ExamTrack param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        examTrackDispatcher.update(param);
        return toResult();
    }

    private void checkForm(ExamTrack param, int event) {
        String id = Optional.ofNullable(param.getId()).orElse("");
        if(EventConstants.EVENT_UPDATE == event && "".equals(id)) {
            throw new TrainingException("更新操作时主键不能空");
        }

        Optional.ofNullable(param.getSubject()).orElseThrow(() ->new TrainingException("所属科目不能空"));

        fillDefaultFields(param, event);
        param.setUserId(param.getUpdatedUser());
        if(EventConstants.EVENT_INSERT == event) {
            //此处总题量要从全局设置取，在buildExamPaper已经填入
//            param.setTotal(100);
            param.setSort(0);
            param.setStartTime(new Date());
        } else if(EventConstants.EVENT_UPDATE == event) {
            param.setEndTime(new Date());
        }
    }
}