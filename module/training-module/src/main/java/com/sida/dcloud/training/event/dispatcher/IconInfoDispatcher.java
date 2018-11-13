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
import com.sida.dcloud.training.event.actor.IconInfoActor;
import com.sida.dcloud.training.po.IconInfo;
import com.sida.dcloud.training.service.IconInfoService;
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
public class IconInfoDispatcher extends BaseClientConfig implements IEventDispatcher<IconInfo> {
    private final static Logger LOG = LoggerFactory.getLogger(IconInfoDispatcher.class);

    @Autowired
    private IconInfoService iconInfoService;
    @Autowired
    private BaseClientConfig baseClientConfig;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(IconInfoActor.class.getSimpleName());
    }

    public void insert(IconInfo po) {
        if(iconInfoService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_ICON_INFO_INSERT, UUIDGenerate.getNextId(), 2);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void update(IconInfo po) {
        if(iconInfoService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_ICON_INFO_UPDATE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void remove(String stringIds) {
        IconInfo po = new IconInfo();
        po.setStringIds(stringIds);
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_ICON_INFO_REMOVE,  UUIDGenerate.getNextId(), 2);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }
}
