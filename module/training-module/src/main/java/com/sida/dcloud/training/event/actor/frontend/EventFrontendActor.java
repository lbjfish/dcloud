package com.sida.dcloud.training.event.actor.frontend;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import com.sida.dcloud.event.po.EventMessage;
import com.sida.dcloud.event.po.EventMessage.JobFailed;
import com.sida.dcloud.service.event.config.EventConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.sida.dcloud.event.po.EventMessage.BACKEND_REGISTRATION;

/**
 * Created by Xiruo on 2017/7/3.
 */
@Component(EventConstants.FRONTEND_ACTOR_NAME)
@Scope("prototype")
public class EventFrontendActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(EventFrontendActor.class);

    private List<ActorRef> backends = new ArrayList<ActorRef>();
    private int jobCounter = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EventMessage.EventJob.class, job -> backends.isEmpty(), job -> {
                    getSender().tell(
                            new JobFailed("Service unavailable, try again later", job),
                            getSender());
                })
                .match(EventMessage.EventJob.class, job -> {
                    jobCounter++;
                    backends.get(jobCounter % backends.size())
                            .forward(job, getContext());
                })
                .matchEquals(BACKEND_REGISTRATION, x -> {
                    getContext().watch(getSender());
                    backends.add(getSender());
                })
                .match(Terminated.class, terminated -> {
                    backends.remove(terminated.getActor());
                })
                .build();
    }

}
