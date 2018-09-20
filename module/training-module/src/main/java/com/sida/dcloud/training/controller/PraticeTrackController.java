package com.sida.dcloud.training.controller;

import com.sida.dcloud.training.service.PraticeTrackService;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("praticeTrack")
@Api(description = "练习日志，经协商功能由前端完成，废弃")
public class PraticeTrackController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PraticeTrackController.class);

    @Autowired
    private PraticeTrackService praticeTrackService;
}