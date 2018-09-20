package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.service.SysShortMessageService;
import com.sida.xiruo.xframework.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sysShortMessage")
public class SysShortMessageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysShortMessageController.class);

    @Autowired
    private SysShortMessageService sysShortMessageService;
}