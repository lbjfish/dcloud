package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.model.ErrorNoteModel;
import com.sida.dcloud.training.po.ErrorNote;
import com.sida.dcloud.training.uow.ErrorNoteUnitOfWork;
import com.sida.dcloud.xdomain.repositories.IUnitOfWorkRepositoryContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Xiruo on 2018/7/27.
 */
@Component("ErrorNoteActor")
@Scope("prototype")
public class ErrorNoteActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(ErrorNoteActor.class);

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
                    IUnitOfWorkRepositoryContext unitOfWork = new ErrorNoteUnitOfWork();
                    ErrorNote po;
                    ErrorNoteModel model;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_ERROR_NOTE_INSERT:
                            po = (ErrorNote)job.getEvent().getEventEntity();
                            model = new ErrorNoteModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ErrorNoteModel.createModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_ERROR_NOTE_CORRECT:
                            po = (ErrorNote)job.getEvent().getEventEntity();
                            po.setRightTime(new Date());

                            model = new ErrorNoteModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ErrorNoteModel.modifyModel(model);
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
