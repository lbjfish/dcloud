package com.sida.dcloud.content.controller;

import com.sida.dcloud.content.po.SpecialSubject;
import com.sida.dcloud.content.service.SpecialSubjectService;
import com.sida.dcloud.content.vo.SpecialSubjectVo;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("specialSubject")
@Api(description = "资讯主题讯息")
public class SpecialSubjectController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SpecialSubjectController.class);

    @Autowired
    private SpecialSubjectService specialSubjectService;

    @RequestMapping(value = "/findSubjectInfoByCategory", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动商品id获取信息")
    public Object findOne(@RequestParam("subjectCategory") @ApiParam("专题分类")String subjectCategory) {
        SpecialSubjectVo one = specialSubjectService.findSpecialToChildsByType(subjectCategory);
        return toResult(one);
    }
}