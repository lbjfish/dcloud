package com.sida.dcloud.job.controller;

import com.sida.dcloud.job.po.JobEntity;
import com.sida.dcloud.job.elastic.consumer.ThirdPartCodeConsumer;
import com.sida.dcloud.job.elastic.consumer.UpdateOrderStatusConsumer;
import com.sida.dcloud.job.util.JobUtil;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by
 * 2017/10/27.
 */
@RestController
@RequestMapping("test")
@Api(description = "计划任务")
public class JobController extends BaseController {
    private final static Logger LOG = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ThirdPartCodeConsumer thirdPartCodeConsumer;
    @Autowired
    private UpdateOrderStatusConsumer updateOrderStatusConsumer;
    @Autowired
    private JobUtil jobUtil;

    @RequestMapping(value = "/createJob", method = RequestMethod.POST)
    @ApiOperation(value = "增加新任务")
    public Object newJob(@RequestBody @ApiParam("任务json")JobEntity jobEntity) {
        LOG.info("新增任务");
        return toResult(thirdPartCodeConsumer.initJob(jobEntity));
    }

    @RequestMapping(value = "/dropJob", method = RequestMethod.GET)
    @ApiOperation(value = "停止任务")
    public Object dropJob(@RequestParam(name = "jobId", required = true) @ApiParam("任务id")String jobId) {
        LOG.info("停止任务");

        return toResult(jobUtil.dropDefaultJob(jobId));
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询任务")
    public Object query(@RequestBody @ApiParam("任务json")JobEntity jobEntity) {
        LOG.info("查询任务");

        return toResult();
    }

    @RequestMapping(value = "/createJobWithOrderStatus", method = RequestMethod.POST)
    @ApiOperation(value = "创建订单状态任务")
    public Object createJobWithOrderStatus(@RequestBody @ApiParam("任务json")JobEntity jobEntity) {
        LOG.info("创建订单状态任务");
        jobEntity.setTrigger(true);
        return toResult(updateOrderStatusConsumer.initJob(jobEntity));
    }

    @RequestMapping(value = "/job/dropJobWithOrderStatus", method = RequestMethod.GET)
    @ApiOperation(value = "删除订单状态任务")
    Object dropJobWithOrderStatus(@RequestParam("jobId") @ApiParam("任务id")String jobId) {
        LOG.info("删除订单状态任务");

        return toResult(jobUtil.dropDefaultJob(jobId));
    }
}
