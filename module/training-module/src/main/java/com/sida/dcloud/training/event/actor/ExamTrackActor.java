package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.util.ExamPaperBuilder;
import com.sida.dcloud.training.util.TrainingCacheUtil;
import com.sida.dcloud.training.model.ExamTrackModel;
import com.sida.dcloud.training.po.ExamTrack;
import com.sida.dcloud.training.uow.ExamTrackUnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Xiruo on 2018/7/27.
 */
@Component("ExamTrackActor")
@Scope("prototype")
public class ExamTrackActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(ExamTrackActor.class);

    @Autowired
    private ActorRef actorRef;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EventMessage.EventJob.class, job -> job.getEvent() == null, job -> {
                    getSender().tell(
                            new JobFailed("事件不能为空", job),
                            getSender());
                })
                .match(EventMessage.EventJob.class, job -> {
                    ExamTrackUnitOfWork unitOfWork = new ExamTrackUnitOfWork();
                    ExamTrack po = null;
                    ExamTrackModel model = null;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_EXAM_TRACK_START:
                            po = (ExamTrack)job.getEvent().getEventEntity();
                            //抽题
                            ExamTrack paper = ExamPaperBuilder.createPaper(po).shareCache(trainingCacheUtil).buildPaper().getExamPaper();
                            //表示从缓存中取试卷
                            if(paper != po) {
                                return;
                            }
                            model = new ExamTrackModel(paper);
                            model.bindUnitOfWork(unitOfWork);
                            ExamTrackModel.createModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_EXAM_TRACK_END:
                            po = (ExamTrack)job.getEvent().getEventEntity();
                            model = new ExamTrackModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ExamTrackModel.modifyModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_EXAM_TRACK_REMOVE:
                            po = (ExamTrack)job.getEvent().getEventEntity();
                            model = new ExamTrackModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ExamTrackModel.removeModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_EXAM_TRACK_SHUFFLE:
                            po = (ExamTrack)job.getEvent().getEventEntity();
                            model = new ExamTrackModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            ExamTrackModel.shuffleModel(model);
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
