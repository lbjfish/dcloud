package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.model.AudioSimulatorModel;
import com.sida.dcloud.training.po.AudioSimulator;
import com.sida.dcloud.training.service.AudioSimulatorService;
import com.sida.dcloud.xdomain.repositories.IUnitOfWorkRepositoryContext;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Xiruo on 2018/7/27.
 */
@Component("AudioSimulatorActor")
@Scope("prototype")
public class AudioSimulatorActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(AudioSimulatorActor.class);

    @Autowired
    private ActorRef actorRef;
    @Autowired
    private AudioSimulatorService audioSimulatorService;

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
                    AudioSimulator po;
                    AudioSimulatorModel model;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_AUDIO_SIMULATOR_INSERT:
                            po = (AudioSimulator)job.getEvent().getEventEntity();
                            if(audioSimulatorService.checkMultiCountByUnique(po) > 0) {
                                throw new TrainingException("名称和编码不能重复");
                            }
                            model = new AudioSimulatorModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            AudioSimulatorModel.createModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_AUDIO_SIMULATOR_UPDATE:
                            po = (AudioSimulator)job.getEvent().getEventEntity();
                            if(audioSimulatorService.checkMultiCountByUnique(po) > 0) {
                                throw new TrainingException("名称和编码不能重复");
                            }
                            model = new AudioSimulatorModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            AudioSimulatorModel.modifyModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_AUDIO_SIMULATOR_REMOVE:
                            po = (AudioSimulator)job.getEvent().getEventEntity();
                            model = new AudioSimulatorModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            AudioSimulatorModel.removeModel(model);
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
