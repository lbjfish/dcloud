package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.dto.GroupCountDto;
import com.sida.dcloud.training.event.dispatcher.ChapterSectionDispatcher;
import com.sida.dcloud.training.event.dispatcher.SpecialGroupDispatcher;
import com.sida.dcloud.training.model.ExerciseModel;
import com.sida.dcloud.training.po.Exercise;
import com.sida.dcloud.training.service.ExerciseService;
import com.sida.dcloud.xdomain.repositories.IUnitOfWorkRepositoryContext;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Xiruo on 2018/7/27.
 */
@Component("ExerciseActor")
@Scope("prototype")
public class ExerciseActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(ExerciseActor.class);

    @Autowired
    private ActorRef actorRef;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private SpecialGroupDispatcher specialGroupDispatcher;
    @Autowired
    private ChapterSectionDispatcher chapterSectionDispatcher;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EventMessage.EventJob.class, job -> job.getEvent() == null, job -> {
                    getSender().tell(
                            new JobFailed("事件不能为空", job),
                            getSender());
                })
                .match(EventMessage.EventJob.class, job -> {
                    IUnitOfWorkRepositoryContext unitOfWork = new MybatisUnitOfWork();
                    Exercise po = null;
                    ExerciseModel model = null;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_EXERCISE_INSERT:
                            po = (Exercise)job.getEvent().getEventEntity();
                            model = new ExerciseModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ExerciseModel.createModel(model);
                            unitOfWork.commit();
                            //发送一个消息到习题分组
                            specialGroupDispatcher.recalculateGroupTotal(String.join(",", po.getGroupIds()), 1, job.getEvent().getTransactionId(), job.getEvent().getTransactionSize());
                            //发送一个消息到章节
                            chapterSectionDispatcher.recalculateSectionTotal(po.getSectionId(), 1, job.getEvent().getTransactionId(), job.getEvent().getTransactionSize());
                            break;
                        case TRAINING_EVENT_EXERCISE_UPDATE:
                            po = (Exercise)job.getEvent().getEventEntity();
                            model = new ExerciseModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ExerciseModel.modifyModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_EXERCISE_REMOVE:
                            po = (Exercise)job.getEvent().getEventEntity();
                            List<GroupCountDto> sectionList = exerciseService.findRemoveCountSection(po.getStringIds());
                            List<GroupCountDto> groupList = exerciseService.findRemoveCountGroup(po.getStringIds());
                            model = new ExerciseModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ExerciseModel.removeModel(model);
                            unitOfWork.commit();
                            //发送一个消息到章节
                            sectionList.forEach(
                                sectionCount -> chapterSectionDispatcher.recalculateSectionTotal(sectionCount.getGroupId(), sectionCount.getCount(), job.getEvent().getTransactionId(), job.getEvent().getTransactionSize())
                            );
                            //发送一个消息到习题分组
                            groupList.forEach(
                                    groupCount -> specialGroupDispatcher.recalculateGroupTotal(groupCount.getGroupId(), groupCount.getCount(), job.getEvent().getTransactionId(), job.getEvent().getTransactionSize())
                            );
                            break;
                    }

                    //此处统一消息发到EventFrontendActor，由它转发到事件中心
                    actorRef.forward(job, getContext());
                    getSender().tell(new EventMessage.EventResult("succeed..."), getSelf());
                }).matchAny(obj -> {
                    getSender().tell(
                            "没有匹配到任何消息处理逻辑",
                            getSender());
                }).build();
    }

}
