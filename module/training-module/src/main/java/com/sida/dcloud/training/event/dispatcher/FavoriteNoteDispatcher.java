package com.sida.dcloud.training.event.dispatcher;

import akka.Done;
import akka.actor.ActorRef;
import akka.util.Timeout;
import com.sida.dcloud.event.po.Event;
import com.sida.dcloud.event.po.EventMessage.EventJob;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.service.event.config.BaseClientConfig;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.event.actor.FavoriteNoteActor;
import com.sida.dcloud.training.po.FavoriteNote;
import com.sida.dcloud.training.service.FavoriteNoteService;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

@Component
public class FavoriteNoteDispatcher extends BaseClientConfig implements IEventDispatcher<FavoriteNote> {
    private final static Logger LOG = LoggerFactory.getLogger(FavoriteNoteDispatcher.class);

    @Autowired
    private FavoriteNoteService favoriteNoteService;
    @Autowired
    private BaseClientConfig baseClientConfig;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(FavoriteNoteActor.class.getSimpleName());
    }

    public void insert(FavoriteNote po) {
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_FAVORITE_NOTE_INSERT);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void update(FavoriteNote po) {
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_FAVORITE_NOTE_UPDATE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void remove(String stringIds) {
        FavoriteNote po = new FavoriteNote();
        po.setStringIds(stringIds);
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_FAVORITE_NOTE_REMOVE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }
}
