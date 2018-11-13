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
import com.sida.dcloud.training.event.actor.ExerciseActor;
import com.sida.dcloud.training.po.Exercise;
import com.sida.dcloud.training.service.ExerciseService;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

@Component
public class ExerciseDispatcher extends BaseClientConfig implements IEventDispatcher<Exercise> {
    private final static Logger LOG = LoggerFactory.getLogger(ExerciseDispatcher.class);

    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private BaseClientConfig baseClientConfig;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(ExerciseActor.class.getSimpleName());
    }

    public void insert(Exercise po) {
        if(exerciseService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_EXERCISE_INSERT, UUIDGenerate.getNextId(), 3);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartExerciseCacheInRedis(po, TrainingEventType.TRAINING_EVENT_EXERCISE_INSERT);
                    return Done.getInstance();
                });
    }

    public void update(Exercise po) {
        if(exerciseService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_EXERCISE_UPDATE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartExerciseCacheInRedis(po, TrainingEventType.TRAINING_EVENT_EXERCISE_UPDATE);
                    return Done.getInstance();
                });
    }

    public void remove(String stringIds) {
        Exercise po = new Exercise();
        po.setStringIds(stringIds);
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_EXERCISE_REMOVE,  UUIDGenerate.getNextId(), 3);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartExerciseCacheInRedis(po, TrainingEventType.TRAINING_EVENT_EXERCISE_REMOVE);
                    return Done.getInstance();
                });
    }
}
