package com.sida.dcloud.operation.controller;

import com.sida.dcloud.operation.service.UserImagesService;
import com.sida.xiruo.xframework.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userImages")
public class UserImagesController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserImagesController.class);

    @Autowired
    private UserImagesService userImagesService;
}