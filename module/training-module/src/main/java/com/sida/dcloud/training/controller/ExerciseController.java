package com.sida.dcloud.training.controller;

import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.util.TrainingCacheUtil;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.dispatcher.ErrorNoteDispatcher;
import com.sida.dcloud.training.event.dispatcher.ExerciseDispatcher;
import com.sida.dcloud.training.po.Exercise;
import com.sida.dcloud.training.service.ExerciseService;
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
@RequestMapping("exercise")
@Api(description = "习题库")
public class ExerciseController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);

    @Autowired
    private ExerciseDispatcher exerciseDispatcher;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private ErrorNoteDispatcher errorNoteDispatcher;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查习题信息列表")
    public Object list(@RequestBody @ApiParam("JSON参数") Exercise param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sequence");
            param.setOrderString("asc");
            return "";
        });
        Object object = exerciseService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findByGroupId", method = RequestMethod.POST)
    @ApiOperation(value = "根据分组id取所有习题")
    public Object findByGroupId(@RequestBody  @ApiParam("groupId") String groupId) {
        Exercise param = new Exercise();
        param.setOrderField("sequence");
        param.setOrderString("asc");
        param.setGroupId(groupId);
        Object object = exerciseService.selectByCondition(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findBySectionId", method = RequestMethod.POST)
    @ApiOperation(value = "根据章节id取所有习题")
    public Object findBySectionId(@RequestBody  @ApiParam("sectionId") String sectionId) {
        Exercise param = new Exercise();
        param.setOrderField("sequence");
        param.setOrderString("asc");
        param.setSectionId(sectionId);
        Object object = exerciseService.selectByCondition(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "根据习题信息主键id获取信息")
    public Object findOne(@RequestBody @ApiParam("id")String id) {
        Exercise one = exerciseService.selectByPrimaryKey(id);
        return toResult(one);
    }

    /********************************************************************************/
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除习题信息")
    public Object remove(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new TrainingException("没有指定要删除的项目");
        }
        exerciseDispatcher.remove(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增习题信息")
    public Object insert(@RequestBody @ApiParam("JSON参数") Exercise param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        exerciseDispatcher.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新习题信息")
    public Object update(@RequestBody @ApiParam("JSON参数") Exercise param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        exerciseDispatcher.update(param);
        return toResult();
    }

    private void checkForm(Exercise param, int event) {
        String id = Optional.ofNullable(param.getId()).orElse("");
        if(EventConstants.EVENT_UPDATE == event && "".equals(id)) {
            throw new TrainingException("更新操作时主键不能空");
        }

        Optional.ofNullable(param.getQuestion()).orElseThrow(() ->new TrainingException("内容不能空"));
        Optional.ofNullable(param.getItems()).orElseThrow(() ->new TrainingException("选项不能空"));
        Optional.ofNullable(param.getSectionId()).orElseThrow(() ->new TrainingException("所属章节不能空"));
        Optional.ofNullable(param.getType()).orElseThrow(() ->new TrainingException("题型不能空"));
        Optional.ofNullable(param.getStyle()).orElseThrow(() ->new TrainingException("表现形式不能空"));
        Optional.ofNullable(param.getRegionId()).orElseThrow(() ->new TrainingException("所属区域不能空"));
        Optional.ofNullable(param.getAnswer()).orElseThrow(() ->new TrainingException("答案不能空"));
        Optional.ofNullable(param.getRemark()).orElseThrow(() ->new TrainingException("解析不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
        }
    }
}