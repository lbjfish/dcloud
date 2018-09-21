package com.sida.dcloud.activity.event.dispatcher;

import akka.Done;
import akka.actor.ActorRef;
import akka.util.Timeout;
import com.sida.dcloud.activity.event.actor.ActivityInfoActor;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.event.po.Event;
import com.sida.dcloud.event.po.EventMessage.EventJob;
import com.sida.dcloud.event.po.activity.ActivityEventType;
import com.sida.dcloud.service.event.config.BaseClientConfig;
import com.sida.dcloud.service.event.config.EventConstants;
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
public class ActivityInfoDispatcher extends BaseClientConfig implements IEventDispatcher<ActivityInfo> {
    private final static Logger LOG = LoggerFactory.getLogger(ActivityInfoDispatcher.class);

    @Autowired
    private BaseClientConfig baseClientConfig;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(ActivityInfoActor.class.getSimpleName());
    }

    public void insert(ActivityInfo po) {
//        if(ActivityInfoService.checkMultiCountByUnique(po) > 0) {
//            throw new ActivityException("名称和编码不能重复");
//        }
        Event event = Event.makeEvent(po, ActivityEventType.ACTIVITY_EVENT_ACTIVITY_INFO_INSERT, UUID.create().toString(), 2);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void update(ActivityInfo po) {
//        if(ActivityInfoService.checkMultiCountByUnique(po) > 0) {
//            throw new ActivityException("名称和编码不能重复");
//        }
        Event event = Event.makeEvent(po, ActivityEventType.ACTIVITY_EVENT_ACTIVITY_INFO_UPDATE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void remove(String stringIds) {
        ActivityInfo po = new ActivityInfo();
        po.setStringIds(stringIds);
        Event event = Event.makeEvent(po, ActivityEventType.ACTIVITY_EVENT_ACTIVITY_INFO_REMOVE,  UUID.create().toString(), 2);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }
}
