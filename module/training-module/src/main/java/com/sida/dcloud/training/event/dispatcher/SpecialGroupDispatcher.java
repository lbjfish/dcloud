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
import com.sida.dcloud.training.event.actor.SpecialGroupActor;
import com.sida.dcloud.training.po.SpecialGroup;
import com.sida.dcloud.training.service.ExerciseService;
import com.sida.dcloud.training.service.SpecialGroupService;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

@Component
public class SpecialGroupDispatcher extends BaseClientConfig implements IEventDispatcher<SpecialGroup> {
    private final static Logger LOG = LoggerFactory.getLogger(SpecialGroupDispatcher.class);

    @Autowired
    private SpecialGroupService specialGroupService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private BaseClientConfig baseClientConfig;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(SpecialGroupActor.class.getSimpleName());
    }

    public void recalculateGroupTotal(String groupIds, int sum, String transactionId, int transactionSize) {
        SpecialGroup po = new SpecialGroup();
        po.setStringIds(groupIds);
        po.setIncrease(sum);

        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_SPECIAL_GROUP_INCREASE_TOTAL, transactionId, transactionSize);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartSpecialGroupCacheInRedis(po, TrainingEventType.TRAINING_EVENT_SPECIAL_GROUP_INCREASE_TOTAL);
                    return Done.getInstance();
                });
//        actorRef.tell(new EventJob(event), actorRef);
    }

    public void insert(SpecialGroup po) {
        if(specialGroupService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_SPECIAL_GROUP_INSERT);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartSpecialGroupCacheInRedis(po, TrainingEventType.TRAINING_EVENT_SPECIAL_GROUP_INSERT);
                    return Done.getInstance();
                });
    }

    public void update(SpecialGroup po) {
        if(specialGroupService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_SPECIAL_GROUP_UPDATE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartSpecialGroupCacheInRedis(po, TrainingEventType.TRAINING_EVENT_SPECIAL_GROUP_UPDATE);
                    return Done.getInstance();
                });
    }

    public void remove(String stringIds) {
        //此处调用方法判断是否有习题属于要删除的分组，没有才能删除
        int size = exerciseService.selectExerciseSizeByGroupIds(stringIds);
        if(size > 0) {
            throw new TrainingException("已经有习题属于要删除的分组，不允许删除");
        }
        SpecialGroup po = new SpecialGroup();
        po.setStringIds(stringIds);
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_SPECIAL_GROUP_REMOVE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartSpecialGroupCacheInRedis(po, TrainingEventType.TRAINING_EVENT_SPECIAL_GROUP_REMOVE);
                    return Done.getInstance();
                });
    }
}
