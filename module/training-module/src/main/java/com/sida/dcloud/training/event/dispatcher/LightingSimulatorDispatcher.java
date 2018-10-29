package com.sida.dcloud.training.event.dispatcher;

import akka.Done;
import akka.actor.ActorRef;
import akka.util.Timeout;
import com.sida.dcloud.event.po.Event;
import com.sida.dcloud.event.po.EventMessage.EventJob;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.service.event.config.BaseClientConfig;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.actor.LightingSimulatorActor;
import com.sida.dcloud.training.po.LightingSimulator;
import com.sida.dcloud.training.service.LightingSimulatorService;
import com.sida.dcloud.training.service.LightingStepsService;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import com.sida.xiruo.xframework.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

@Component
public class LightingSimulatorDispatcher extends BaseClientConfig implements IEventDispatcher<LightingSimulator> {
    private final static Logger LOG = LoggerFactory.getLogger(LightingSimulatorDispatcher.class);

    @Autowired
    private LightingSimulatorService lightingSimulatorService;
    @Autowired
    private LightingStepsService lightingStepsService;
    @Autowired
    private BaseClientConfig baseClientConfig;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(LightingSimulatorActor.class.getSimpleName());
    }

    public void insert(LightingSimulator po) {
        if(lightingSimulatorService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_LIGHTING_SIMULATOR_INSERT, UUIDGenerate.getNextId(), 2);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void update(LightingSimulator po) {
        if(lightingSimulatorService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_LIGHTING_SIMULATOR_UPDATE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void remove(String stringIds) {
        //此处调用IconInfoMapper的方法判断是否有图标属于要删除的分组，没有才能删除
        int size = lightingStepsService.selectLightingSimulatorSizeByStringIds(stringIds);
        if(size > 0) {
            throw new TrainingException("请清空步骤后删除此信息");
        }
        LightingSimulator po = new LightingSimulator();
        po.setStringIds(stringIds);
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_LIGHTING_SIMULATOR_REMOVE,  UUIDGenerate.getNextId(), 2);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }
}
