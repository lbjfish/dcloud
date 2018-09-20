package com.sida.dcloud.training.event.dispatcher;

import akka.Done;
import akka.actor.ActorRef;
import akka.util.Timeout;
import com.sida.dcloud.event.po.Event;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.service.event.config.BaseClientConfig;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.actor.TrainingUserSettingActor;
import com.sida.dcloud.training.po.TrainingUserSetting;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

public class TrainingUserSettingDispatcher extends BaseClientConfig implements IEventDispatcher<TrainingUserSetting> {
    private final static Logger LOG = LoggerFactory.getLogger(TrainingUserSettingDispatcher.class);

    @Autowired
    private BaseClientConfig baseClientConfig;

    @Auto
    private TrainingUserSettingActor trainingUserSettingActor;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(TrainingUserSettingActor.class.getSimpleName());
    }

    @Override
    public void insert(TrainingUserSetting entity) {
        throw new TrainingException("非法调用");
    }

    @Override
    public void update(TrainingUserSetting dto) {
        Event event = Event.makeEvent(dto, TrainingEventType.TRAINING_EVENT_TRAINING_USER_SETTING_SAVE);

        ask(actorRef, new EventMessage.EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    @Override
    public void remove(String stringIds) {
        throw new TrainingException("非法调用");
    }
}
