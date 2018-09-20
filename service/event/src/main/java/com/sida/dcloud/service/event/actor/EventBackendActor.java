package com.sida.dcloud.service.event.actor;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.Member;
import akka.cluster.MemberStatus;
import com.sida.dcloud.event.po.EventMessage.EventJob;
import com.sida.dcloud.event.po.EventMessage.EventResult;
import com.sida.dcloud.service.event.config.EventConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.sida.dcloud.event.po.EventMessage.BACKEND_REGISTRATION;

/**
 * Created by Xiruo on 2017/7/3.
 */
@Component("eventBackendActor")
@Scope("prototype")
public class EventBackendActor extends AbstractActor {
    private final static Logger LOG = LoggerFactory.getLogger(EventBackendActor.class);

    private Cluster cluster = Cluster.get(getContext().system());

//    @Autowired
//    private EventRepository eventRepository;

    @Override
    public void preStart() throws Exception {
        cluster.subscribe(getSelf(), MemberUp.class);
    }

    @Override
    public void postStop() throws Exception {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EventResult.class, result -> LOG.info(result.getText()))
                .match(EventJob.class, job -> {
                    //此处保存event，便于event sourcing
                    // todo
//                    eventRepository.save(job.getEvent());
                    LOG.info(job.getEvent().toString());
                    getSender().tell(new EventResult("succeed for backend..."),
                            getSelf());
                })
                .match(CurrentClusterState.class, state -> {
                    for (Member member : state.getMembers()) {
                        if (member.status().equals(MemberStatus.up())) {
                            register(member);
                        }
                    }
                })
                .match(MemberUp.class, mUp -> {
                    register(mUp.member());
                })
                .build();
    }

    private void register(Member member) {
        if (member.hasRole("frontend")) {
//            LOG.info("Address for member is {}", member.address());
            getContext().actorSelection(member.address() + "/user/" + EventConstants.FRONTEND_ACTOR_NAME).tell(BACKEND_REGISTRATION, getSelf());
        }
    }
}
