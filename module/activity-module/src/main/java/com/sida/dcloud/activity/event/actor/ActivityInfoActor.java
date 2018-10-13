package com.sida.dcloud.activity.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.service.ActivityInfoService;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.activity.ActivityEventType;
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
//@Component("ActivityInfoActor")
//@Scope("prototype")
public class ActivityInfoActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(ActivityInfoActor.class);

    @Autowired
    private ActorRef actorRef;
    @Autowired
    private ActivityInfoService activityInfoService;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EventMessage.EventJob.class, job -> job.getEvent() == null, job -> {
                    getSender().tell(
                            new JobFailed("事件不能为空", job),
                            getSender());
                })
                .match(EventMessage.EventJob.class, job -> {
//                    IUnitOfWorkRepositoryContext unitOfWork = new MybatisUnitOfWork();
//                    ActivityInfo po;
//                    ActivityInfoModel model;
//                    switch((ActivityEventType)job.getEvent().getEventType()) {
//                        case ACTIVITY_EVENT_ACTIVITY_INFO_INSERT:
//                            po = (ActivityInfo)job.getEvent().getEventEntity();
//                            if(activityInfoService.checkMultiCountByUnique(po) > 0) {
//                                throw new ActivityException("名称和编码不能重复");
//                            }
//                            model = new ActivityInfoModel(po);
//                            model.bindUnitOfWork(unitOfWork);
//                            ActivityInfoModel.createModel(model);
//                            unitOfWork.commit();
//                            break;
//                        case ACTIVITY_EVENT_ACTIVITY_INFO_UPDATE:
//                            po = (ActivityInfo)job.getEvent().getEventEntity();
//                            if(activityInfoService.checkMultiCountByUnique(po) > 0) {
//                                throw new ActivityException("名称和编码不能重复");
//                            }
//                            model = new ActivityInfoModel(po);
//                            model.bindUnitOfWork(unitOfWork);
//                            ActivityInfoModel.modifyModel(model);
//                            unitOfWork.commit();
//                            break;
//                        case ACTIVITY_EVENT_ACTIVITY_INFO_REMOVE:
//                            po = (ActivityInfo)job.getEvent().getEventEntity();
//                            model = new ActivityInfoModel(po);
//                            model.bindUnitOfWork(unitOfWork);
//                            ActivityInfoModel.removeModel(model);
//                            unitOfWork.commit();
//                            break;
//                    }

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
