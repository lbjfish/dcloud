package com.sida.dcloud.training.controller;

import com.sida.dcloud.training.util.TrainingTimingJob;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job")
@Api(description = "定时任务")
public class TrainingJobController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(TrainingJobController.class);

    @Autowired
    private TrainingTimingJob trainingTimingJob;

    @RequestMapping(value = "/scanExamPaper", method = RequestMethod.GET)
    @ApiOperation(value = "在redis扫描考试试卷")
    public void scanExamPaper() {
        trainingTimingJob.scanExamPaperJob();
    }
}
