package com.sida.dcloud.schedule.controller;

import com.sida.dcloud.schedule.service.JobEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("jobManage")
public class JobManageController {

    @Autowired
    private JobEntityService JobEntityService;

    @RequestMapping(value = "/reload", method = RequestMethod.GET)
    @ResponseBody
    public String reload() {
        JobEntityService.reloadSchedule();
        return "success";
    }
}
