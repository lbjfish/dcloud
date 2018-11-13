package com.sida.dcloud.activity.client;

import com.sida.dcloud.job.po.JobEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${provider.job-service}", fallback = JobClientHystrix.class)
public interface JobClient {
    @RequestMapping(value = "/job/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询任务")
    Object query(@RequestBody @ApiParam("任务json") JobEntity jobEntity);

    @RequestMapping(value = "/job/createJobWithOrderStatus", method = RequestMethod.POST)
    @ApiOperation(value = "创建订单状态任务")
    Object createJobWithOrderStatus(@RequestBody @ApiParam("任务json")JobEntity jobEntity);

    @RequestMapping(value = "/job/dropJobWithOrderStatus", method = RequestMethod.GET)
    @ApiOperation(value = "删除订单状态任务")
    Object dropJobWithOrderStatus(@RequestParam("jobName") @ApiParam("任务名称")String jobName);
}
