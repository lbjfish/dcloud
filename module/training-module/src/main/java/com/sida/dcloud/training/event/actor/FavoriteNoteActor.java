package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.model.FavoriteNoteModel;
import com.sida.dcloud.training.po.FavoriteNote;
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
@Component("FavoriteNoteActor")
@Scope("prototype")
public class FavoriteNoteActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(FavoriteNoteActor.class);

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
                    IUnitOfWorkRepositoryContext unitOfWork = new MybatisUnitOfWork();
                    FavoriteNote po = null;
                    FavoriteNoteModel model = null;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_FAVORITE_NOTE_INSERT:
                            po = (FavoriteNote)job.getEvent().getEventEntity();
                            model = new FavoriteNoteModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            FavoriteNoteModel.createModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_FAVORITE_NOTE_UPDATE:
                            po = (FavoriteNote)job.getEvent().getEventEntity();
                            model = new FavoriteNoteModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            FavoriteNoteModel.modifyModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_FAVORITE_NOTE_REMOVE:
                            po = (FavoriteNote)job.getEvent().getEventEntity();
                            model = new FavoriteNoteModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            FavoriteNoteModel.removeModel(model);
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
