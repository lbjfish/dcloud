package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.service.ActivityInfoService;
import com.sida.xiruo.xframework.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("activityInfo")
public class ActivityInfoController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInfoController.class);

    @Autowired
    private ActivityInfoService activityInfoService;
}