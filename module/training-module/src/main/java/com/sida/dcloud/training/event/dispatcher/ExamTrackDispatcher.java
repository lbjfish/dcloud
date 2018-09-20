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
import com.sida.dcloud.training.event.actor.ExamTrackActor;
import com.sida.dcloud.training.po.ExamAnswerTrack;
import com.sida.dcloud.training.po.ExamTrack;
import com.sida.dcloud.training.service.ExamTrackService;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

@Component
public class ExamTrackDispatcher extends BaseClientConfig implements IEventDispatcher<ExamTrack> {
    private final static Logger LOG = LoggerFactory.getLogger(ExamTrackDispatcher.class);

    @Autowired
    private ExamTrackService examTrackService;
    @Autowired
    private BaseClientConfig baseClientConfig;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(ExamTrackActor.class.getSimpleName());
    }

    public void shuffleExam(ExamTrack examTrack) {
        ExamTrack po = new ExamTrack();
        try {
            examTrack.setDirty(false);
            BeanUtils.copyProperties(examTrack, po);
            Map<String, ExamAnswerTrack> map = new HashMap<>();
            //找出dirty的答题日志
            examTrack.getExamAnswerTrackMap().values().stream().filter(answer -> answer.isDirty()).forEach(answer -> {
                answer.setDirty(false);
                map.put(answer.getId(), answer);
            });
            po.setExamAnswerTrackMap(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_EXAM_TRACK_SHUFFLE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void insert(ExamTrack po) {
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_EXAM_TRACK_START);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void update(ExamTrack po) {
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_EXAM_TRACK_END);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }

    public void remove(String stringIds) {
        ExamTrack po = new ExamTrack();
        po.setStringIds(stringIds);
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_EXAM_TRACK_REMOVE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    return Done.getInstance();
                });
    }
}
