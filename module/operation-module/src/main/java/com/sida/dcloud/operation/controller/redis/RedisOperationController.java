package com.sida.dcloud.operation.controller.redis;

import com.sida.dcloud.operation.util.OperationRedisUtil;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("redisOperation")
@Api(description = "redis操作")
public class RedisOperationController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(RedisOperationController.class);

    @Autowired
    private OperationRedisUtil operationRedisUtil;

    /**
     * 从redis清空所有匹配相关数据
     * @return
     */
    @RequestMapping(value = "/clearMatcherInfoInRedis", method = RequestMethod.GET)
    @ApiOperation(value = "从redis清空所有匹配相关数据")
    public Object clearMatcherInfoInRedis() {
        operationRedisUtil.clearMatcherInfoInRedis();
        return toResult();
    }

    /**
     * 从redis清空标签数据
     * @return
     */
    @RequestMapping(value = "/clearTagInfoInRedis", method = RequestMethod.GET)
    @ApiOperation(value = "从redis清空标签数据")
    public Object clearTagInfoInRedis() {
        operationRedisUtil.clearTagInfoInRedis();
        return toResult();
    }

    /**
     * 从redis清空企业数据
     * @return
     */
    @RequestMapping(value = "/clearCompanyInfoInRedis", method = RequestMethod.GET)
    @ApiOperation(value = "从redis清空企业数据")
    public Object clearCompanyInfoInRedis() {
        operationRedisUtil.clearCompanyInfoInRedis();
        return toResult();
    }

    /**
     * 从redis清空企业与标签的关联关系
     * @return
     */
    @RequestMapping(value = "/clearCompanyRelTagInRedis", method = RequestMethod.GET)
    @ApiOperation(value = "从redis清空企业与标签的关联关系")
    public Object clearCompanyRelTagInRedis() {
        operationRedisUtil.clearCompanyRelTagInRedis();
        return toResult();
    }
}
