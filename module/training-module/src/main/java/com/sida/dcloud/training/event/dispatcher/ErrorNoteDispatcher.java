package com.sida.dcloud.training.event.dispatcher;

import akka.Done;
import akka.actor.ActorRef;
import akka.util.Timeout;
import com.sida.dcloud.event.po.Event;
import com.sida.dcloud.event.po.EventMessage.EventJob;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.service.event.config.BaseClientConfig;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.util.TrainingCacheUtil;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.actor.ErrorNoteActor;
import com.sida.dcloud.training.po.ErrorNote;
import com.sida.dcloud.training.service.ErrorNoteService;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

@Component
public class ErrorNoteDispatcher extends BaseClientConfig implements IEventDispatcher<ErrorNote> {
    private final static Logger LOG = LoggerFactory.getLogger(ErrorNoteDispatcher.class);

    @Autowired
    private ErrorNoteService errorNoteService;
    @Autowired
    private BaseClientConfig baseClientConfig;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(ErrorNoteActor.class.getSimpleName());
    }

    /**
     * 产生新错误日志
     */
    @Override
    public void insert(ErrorNote po) {
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_ERROR_NOTE_INSERT);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void update(ErrorNote po) {
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_ERROR_NOTE_CORRECT);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
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
