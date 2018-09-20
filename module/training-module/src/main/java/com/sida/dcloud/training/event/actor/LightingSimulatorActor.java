package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.model.LightingSimulatorModel;
import com.sida.dcloud.training.po.LightingSimulator;
import com.sida.dcloud.training.service.LightingStepsService;
import com.sida.dcloud.training.uow.LightingSimulatorUnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Xiruo on 2018/7/27.
 */
@Component("LightingSimulatorActor")
@Scope("prototype")
public class LightingSimulatorActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(LightingSimulatorActor.class);

    @Autowired
    private ActorRef actorRef;
    @Autowired
    private LightingStepsService lightingStepsService;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EventMessage.EventJob.class, job -> job.getEvent() == null, job -> {
                    getSender().tell(
                            new JobFailed("事件不能为空", job),
                            getSender());
                })
                .match(EventMessage.EventJob.class, job -> {
                    LightingSimulatorUnitOfWork unitOfWork = new LightingSimulatorUnitOfWork();
                    unitOfWork.setLightingStepsService(lightingStepsService);
                    LightingSimulator po;
                    LightingSimulatorModel model;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_LIGHTING_SIMULATOR_INSERT:
                            po = (LightingSimulator)job.getEvent().getEventEntity();
                            model = new LightingSimulatorModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            LightingSimulatorModel.createModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_LIGHTING_SIMULATOR_UPDATE:
                            po = (LightingSimulator)job.getEvent().getEventEntity();
                            model = new LightingSimulatorModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            LightingSimulatorModel.modifyModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_LIGHTING_SIMULATOR_REMOVE:
                            po = (LightingSimulator)job.getEvent().getEventEntity();
                            model = new LightingSimulatorModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            LightingSimulatorModel.removeModel(model);
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
