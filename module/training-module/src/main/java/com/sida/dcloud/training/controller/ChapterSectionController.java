package com.sida.dcloud.training.controller;

import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.dispatcher.ChapterSectionDispatcher;
import com.sida.dcloud.training.po.ChapterSection;
import com.sida.dcloud.training.service.ChapterSectionService;
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
@RequestMapping("chapterSection")
@Api(description = "习题章节")
public class ChapterSectionController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ChapterSectionController.class);

    @Autowired
    private ChapterSectionDispatcher chapterSectionDispatcher;
    @Autowired
    private ChapterSectionService chapterSectionService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查习题章节列表")
    public Object list(@RequestBody @ApiParam("JSON参数") ChapterSection param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = chapterSectionService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "根据习题章节主键id获取信息")
    public Object findOne(@RequestBody @ApiParam("id")String id) {
        ChapterSection one = chapterSectionService.selectByPrimaryKey(id);
        return toResult(one);
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除习题章节")
    public Object remove(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new TrainingException("没有指定要删除的项目");
        }
        chapterSectionDispatcher.remove(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增习题章节")
    public Object insert(@RequestBody @ApiParam("JSON参数") ChapterSection param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        chapterSectionDispatcher.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新习题章节")
    public Object update(@RequestBody @ApiParam("JSON参数") ChapterSection param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        chapterSectionDispatcher.update(param);
        return toResult();
    }

    private void checkForm(ChapterSection param, int event) {
        if(EventConstants.EVENT_UPDATE == event && !Optional.ofNullable(param.getId()).isPresent()) {
            throw new TrainingException("更新操作时主键不能空");
        }

        Optional.ofNullable(param.getName()).orElseThrow(() -> new TrainingException("名字不能空"));
        Optional.ofNullable(param.getCode()).orElseThrow(() -> new TrainingException("编码不能空"));
        Optional.ofNullable(param.getSubject()).orElseThrow(() -> new TrainingException("所属科目不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
            param.setTotal(0);
        }
    }
}