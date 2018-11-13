package com.sida.dcloud.content.controller;

import com.sida.dcloud.content.service.InformationService;
import com.sida.xiruo.xframework.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("information")
public class InformationController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(InformationController.class);

    @Autowired
    private InformationService informationService;
}