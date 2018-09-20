package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.model.ChapterSectionModel;
import com.sida.dcloud.training.po.ChapterSection;
import com.sida.dcloud.training.uow.ChapterSectionUnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Xiruo on 2018/7/27.
 */
@Component("ChapterSectionActor")
@Scope("prototype")
public class ChapterSectionActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(ChapterSectionActor.class);

    @Autowired
    private ActorRef actorRef;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EventMessage.EventJob.class, job -> job.getEvent() == null, job -> {
                    getSender().tell(
                            new JobFailed("事件不能为空", job),
                            getSender());
                })
                .match(EventMessage.EventJob.class, job -> {
                    ChapterSectionUnitOfWork unitOfWork = new ChapterSectionUnitOfWork();
                    ChapterSection po;
                    ChapterSectionModel model;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_CHAPTER_SECTION_INSERT:
                            po = (ChapterSection)job.getEvent().getEventEntity();
                            model = new ChapterSectionModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ChapterSectionModel.createModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_CHAPTER_SECTION_UPDATE:
                            po = (ChapterSection)job.getEvent().getEventEntity();
                            model = new ChapterSectionModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ChapterSectionModel.modifyModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_CHAPTER_SECTION_INCREASE_TOTAL:
                            po = (ChapterSection)job.getEvent().getEventEntity();
                            model = new ChapterSectionModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ChapterSectionModel.increaseTotal(model);
                            break;
                        case TRAINING_EVENT_CHAPTER_SECTION_REMOVE:
                            po = (ChapterSection)job.getEvent().getEventEntity();
                            model = new ChapterSectionModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ChapterSectionModel.removeModel(model);
                            unitOfWork.commit();
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
