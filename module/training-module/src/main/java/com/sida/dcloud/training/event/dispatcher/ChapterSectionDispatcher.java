package com.sida.dcloud.training.event.dispatcher;

import akka.Done;
import akka.actor.ActorRef;
import akka.util.Timeout;
import com.sida.dcloud.event.po.Event;
import com.sida.dcloud.event.po.EventMessage.EventJob;
import com.sida.dcloud.service.event.config.BaseClientConfig;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.training.util.TrainingCacheUtil;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.actor.ChapterSectionActor;
import com.sida.dcloud.training.po.ChapterSection;
import com.sida.dcloud.training.service.ChapterSectionService;
import com.sida.dcloud.training.service.ExerciseService;
import com.sida.dcloud.xdomain.dispatcher.IEventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;
import static com.sida.dcloud.event.po.training.TrainingEventType.*;

@Component
public class ChapterSectionDispatcher extends BaseClientConfig implements IEventDispatcher<ChapterSection> {
    private final static Logger LOG = LoggerFactory.getLogger(ChapterSectionDispatcher.class);

    @Autowired
    private ChapterSectionService chapterSectionService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private BaseClientConfig baseClientConfig;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    private ActorRef actorRef = null;

    @PostConstruct
    public void init() {
        actorRef = baseClientConfig.actorRef(ChapterSectionActor.class.getSimpleName());
    }

    public void recalculateSectionTotal(String sectionId, int sum, String transactionId, int transactionSize) {
        ChapterSection po = chapterSectionService.selectByPrimaryKey(sectionId);
        po.setIncrease(sum);

        Event event = Event.makeEvent(po, TRAINING_EVENT_CHAPTER_SECTION_INCREASE_TOTAL, transactionId, transactionSize);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    //更新缓存
                    trainingCacheUtil.updatePartChapterSectionCacheInRedis(po, TRAINING_EVENT_CHAPTER_SECTION_INCREASE_TOTAL);
                    return Done.getInstance();
                });
//        actorRef.tell(new EventJob(event), actorRef);
    }

    public void insert(ChapterSection po) {
        if(chapterSectionService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TRAINING_EVENT_CHAPTER_SECTION_INSERT);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    //更新缓存
                    trainingCacheUtil.updatePartChapterSectionCacheInRedis(po, TRAINING_EVENT_CHAPTER_SECTION_INSERT);
                    return Done.getInstance();
                });
    }

    public void update(ChapterSection po) {
        if(chapterSectionService.checkMultiCountByUnique(po) > 0) {
            throw new TrainingException("名称和编码不能重复");
        }
        Event event = Event.makeEvent(po, TRAINING_EVENT_CHAPTER_SECTION_UPDATE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    //更新缓存
                    trainingCacheUtil.updatePartChapterSectionCacheInRedis(po, TRAINING_EVENT_CHAPTER_SECTION_UPDATE);
                    return Done.getInstance();
                });
    }

    public void remove(String stringIds) {
        //此处调用IconInfoMapper的方法判断是否有图标属于要删除的分组，没有才能删除
        int size = exerciseService.selectExerciseSizeBySectionIds(stringIds);
        if(size > 0) {
            throw new TrainingException("已经有习题属于要删除的章节，不允许删除");
        }
        ChapterSection po = new ChapterSection();
        po.setStringIds(stringIds);
        Event event = Event.makeEvent(po, TRAINING_EVENT_CHAPTER_SECTION_REMOVE);

        ask(actorRef, new EventJob(event), new Timeout(EventConstants.DEFAULT_EVENT_TIMEOUT, TimeUnit.SECONDS))
                .thenApply(reply -> {
                    LOG.info("reply: " + reply.toString());
                    //更新缓存
                    trainingCacheUtil.updatePartChapterSectionCacheInRedis(po, TRAINING_EVENT_CHAPTER_SECTION_REMOVE);
                    return Done.getInstance();
                });
    }
}
