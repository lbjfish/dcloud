package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.dto.ExamRuleMixDto;
import com.sida.dcloud.training.model.ExamRuleMixModel;
import com.sida.dcloud.training.uow.ExamRuleMixUnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Xiruo on 2018/7/27.
 */
@Component("ExamRuleMixActor")
@Scope("prototype")
public class ExamRuleMixActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(ExamRuleMixActor.class);

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
                    ExamRuleMixUnitOfWork unitOfWork = new ExamRuleMixUnitOfWork();
                    ExamRuleMixDto dto;
                    ExamRuleMixModel model;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_EXAM_RULE_MIX_UPDATE:
                            dto = (ExamRuleMixDto)job.getEvent().getEventEntity();
                            model = new ExamRuleMixModel(dto);
                            model.bindUnitOfWork(unitOfWork);
                            ExamRuleMixModel.modifyModel(model);
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
