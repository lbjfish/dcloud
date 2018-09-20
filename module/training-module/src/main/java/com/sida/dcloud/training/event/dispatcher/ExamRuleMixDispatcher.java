package com.sida.dcloud.training.event.dispatcher;

import akka.Done;
import akka.actor.ActorRef;
import akka.util.Timeout;
import com.sida.dcloud.event.po.Event;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.service.event.config.BaseClientConfig;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.util.TrainingCacheUtil;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.dto.ExamRuleMixDto;
import com.sida.dcloud.training.event.actor.ExamRuleMixActor;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

@Component
public class ExamRuleMixDispatcher extends BaseClientConfig implements IEventDispatcher<ExamRuleMixDto> {
    private final static Logger LOG = LoggerFactory.getLogger(ExamRuleMixDispatcher.class);

    @Autowired
    private BaseClientConfig baseClientConfig;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(ExamRuleMixActor.class.getSimpleName());
    }

    @Override
    public void insert(ExamRuleMixDto entity) {
        throw new TrainingException("非法调用");
    }

    @Override
    public void update(ExamRuleMixDto dto) {
        Event event = Event.makeEvent(dto, TrainingEventType.TRAINING_EVENT_EXAM_RULE_MIX_UPDATE);

        ask(actorRef, new EventMessage.EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    //更新缓存
                    trainingCacheUtil.updatePartExamRuleCacheInRedis(dto, TrainingEventType.TRAINING_EVENT_EXAM_RULE_MIX_UPDATE);
                    return Done.getInstance();
                });
    }

    @Override
    public void remove(String stringIds) {
        throw new TrainingException("非法调用");
    }
}
