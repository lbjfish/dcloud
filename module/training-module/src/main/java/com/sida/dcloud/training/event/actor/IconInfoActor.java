package com.sida.dcloud.training.event.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.dto.GroupCountDto;
import com.sida.dcloud.training.event.dispatcher.IconGroupDispatcher;
import com.sida.dcloud.training.model.IconInfoModel;
import com.sida.dcloud.training.po.IconInfo;
import com.sida.dcloud.training.service.IconInfoService;
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
@Component("IconInfoActor")
@Scope("prototype")
public class IconInfoActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(IconInfoActor.class);

    @Autowired
    private ActorRef actorRef;
    @Autowired
    private IconInfoService iconInfoService;
    @Autowired
    private IconGroupDispatcher iconGroupDispatcher;

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
                    IconInfo po = null;
                    IconInfoModel model = null;
                    switch((TrainingEventType)job.getEvent().getEventType()) {
                        case TRAINING_EVENT_ICON_INFO_INSERT:
                            po = (IconInfo)job.getEvent().getEventEntity();
                            model = new IconInfoModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            IconInfoModel.createModel(model);
                            unitOfWork.commit();
                            //发送一个消息到图标分组
                            iconGroupDispatcher.recalculateGroupTotal(po.getGroupId(), 1, job.getEvent().getTransactionId(), job.getEvent().getTransactionSize());
                            break;
                        case TRAINING_EVENT_ICON_INFO_UPDATE:
                            po = (IconInfo)job.getEvent().getEventEntity();
                            model = new IconInfoModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            IconInfoModel.modifyModel(model);
                            unitOfWork.commit();
                            break;
                        case TRAINING_EVENT_ICON_INFO_REMOVE:
                            po = (IconInfo)job.getEvent().getEventEntity();
                            List<GroupCountDto> list = iconInfoService.findRemoveCountGroup(po.getStringIds());
                            model = new IconInfoModel(po);
                            model.bindUnitOfWork(unitOfWork);
                            IconInfoModel.removeModel(model);
                            unitOfWork.commit();
                            //发送一个消息到图标分组
                            list.forEach(
                                groupCount -> iconGroupDispatcher.recalculateGroupTotal(groupCount.getGroupId(), groupCount.getCount(), job.getEvent().getTransactionId(), job.getEvent().getTransactionSize())
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
