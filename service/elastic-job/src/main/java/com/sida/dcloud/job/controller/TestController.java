package com.sida.dcloud.job.controller;

import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
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
@Api(description = "测试")
public class TestController extends BaseController {
    private final static Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/createJob", method = RequestMethod.GET)
    @ApiOperation(value = "增加新任务")
    public Object newJob(@RequestParam(name = "second", defaultValue = "3") @ApiParam("频率（秒）")int second) {
        LOG.info("新增任务");

        return toResult();
    }

    @RequestMapping(value = "/dropJob", method = RequestMethod.GET)
    @ApiOperation(value = "停止任务")
    public Object dropJob() {
        LOG.info("停止任务");

        return toResult();
    }
}
