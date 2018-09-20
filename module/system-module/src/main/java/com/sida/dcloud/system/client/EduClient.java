package com.sida.dcloud.system.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "${provider.teaching-module}")
public interface EduClient {

    @RequestMapping(value = "/eduCoach/getCoachSimpleList", method = RequestMethod.POST)
    @ApiOperation(value = "根据员工IDS获取教练列表基本信息")
    Object getCoachSimpleList(@RequestBody @ApiParam("JSON参数") List<String> empIds);

    @RequestMapping(value = "/eduCoach/getCountCoach",method = RequestMethod.GET)
    @ApiOperation("首页获取教练人数")
    public Object getCountCoach();


    @RequestMapping(value = "/eduCourseTraining/getCountTraining", method = RequestMethod.GET)
    @ApiOperation(value = "训练场总数 用于首页展示")
    public Object getCountTraining();
}
