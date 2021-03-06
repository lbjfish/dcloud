package com.sida.dcloud.operation.controller;

import com.sida.dcloud.operation.service.CompanyCooperationBrandService;
import com.sida.xiruo.xframework.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("companyCooperationBrand")
public class CompanyCooperationBrandController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyCooperationBrandController.class);

    @Autowired
    private CompanyCooperationBrandService companyCooperationBrandService;
}