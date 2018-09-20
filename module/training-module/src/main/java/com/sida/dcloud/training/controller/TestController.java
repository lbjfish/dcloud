package com.sida.dcloud.training.controller;

import akka.Done;
import akka.actor.ActorRef;
import akka.util.Timeout;
import com.sida.dcloud.auth.po.SysRole;
import com.sida.dcloud.event.po.Event;
import com.sida.dcloud.event.po.EventMessage.EventJob;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

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
    @Autowired
    private ActorRef actorRef;

    @GetMapping("load")
    @ApiOperation("加载初始数据")
    public Object load() {
        redisUtil.set("name", "Xiruo");
        LOG.info("Value for name which in redis is{}", redisUtil.get("name"));
        Event event = Event.makeEvent(new SysRole(), TrainingEventType.TRAINING_EVENT_ICON_GROUP_INSERT);

        ask(actorRef, new EventJob(event), new Timeout(5, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });

        return toResult();
    }
}
