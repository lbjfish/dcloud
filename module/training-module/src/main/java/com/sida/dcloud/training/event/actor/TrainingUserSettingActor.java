package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.model.TrainingUserSettingModel;
import com.sida.dcloud.training.po.TrainingUserSetting;
import com.sida.dcloud.training.uow.TrainingUserSettingUnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Xiruo on 2018/7/27.
 */
@Component("TrainingUserSettingActor")
@Scope("prototype")
public class TrainingUserSettingActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(TrainingUserSettingActor.class);

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
                    TrainingUserSettingUnitOfWork unitOfWork = new TrainingUserSettingUnitOfWork();
                    TrainingUserSetting po = null;
                    TrainingUserSettingModel model = null;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_TRAINING_USER_SETTING_SAVE:
                            po = (TrainingUserSetting)job.getEvent().getEventEntity();
                            model = new TrainingUserSettingModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            TrainingUserSettingModel.save(model);
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
