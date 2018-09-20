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
import com.sida.dcloud.training.event.actor.IconGroupActor;
import com.sida.dcloud.training.po.IconGroup;
import com.sida.dcloud.training.service.IconGroupService;
import com.sida.dcloud.training.service.IconInfoService;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

@Component
public class IconGroupDispatcher extends BaseClientConfig implements IEventDispatcher<IconGroup> {
    private final static Logger LOG = LoggerFactory.getLogger(IconGroupDispatcher.class);

    @Autowired
    private IconGroupService iconGroupService;
    @Autowired
    private IconInfoService iconInfoService;
    @Autowired
    private BaseClientConfig baseClientConfig;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(IconGroupActor.class.getSimpleName());
    }

    public void recalculateGroupTotal(String groupId, int sum, String transactionId, int transactionSize) {
        IconGroup po = iconGroupService.selectByPrimaryKey(groupId);
        po.setIncrease(sum);

        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_ICON_GROUP_INCREASE_TOTAL, transactionId, transactionSize);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartIconGroupCacheInRedis(po, TrainingEventType.TRAINING_EVENT_ICON_GROUP_INCREASE_TOTAL);
                    return Done.getInstance();
                });
    }

    public void insert(IconGroup po) {
        if(iconGroupService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_ICON_GROUP_INSERT);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartIconGroupCacheInRedis(po, TrainingEventType.TRAINING_EVENT_ICON_GROUP_INSERT);
                    return Done.getInstance();
                });
    }

    public void update(IconGroup po) {
        if(iconGroupService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_ICON_GROUP_UPDATE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartIconGroupCacheInRedis(po, TrainingEventType.TRAINING_EVENT_ICON_GROUP_UPDATE);
                    return Done.getInstance();
                });
    }

    public void remove(String stringIds) {
        //此处调用IconInfoMapper的方法判断是否有图标属于要删除的分组，没有才能删除
        int iconSize = iconInfoService.selectIconInfoSizeByGroupIds(stringIds);
        if(iconSize > 0) {
            throw new TrainingException("已经有图标属于要删除的分组，不允许删除");
        }
        IconGroup po = new IconGroup();
        po.setStringIds(stringIds);
        Event event = Event.makeEvent(po, TrainingEventType.TRAINING_EVENT_ICON_GROUP_REMOVE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    trainingCacheUtil.updatePartIconGroupCacheInRedis(po, TrainingEventType.TRAINING_EVENT_ICON_GROUP_REMOVE);
                    return Done.getInstance();
                });
    }
}
