/**
 * FileName：TeachingClient
 * Author：  chenguanshou
 * Date：    2017/12/7 0007 下午 3:43
 */
package com.sida.dcloud.schedule.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${provider.training-module}")
public interface TrainingClient {

    @RequestMapping(value = "/job/scanExamPaper", method = RequestMethod.GET)
    @ApiOperation(value = "学员自动分配")
    void scanExamPaper();
}
