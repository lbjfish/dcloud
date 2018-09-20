package com.sida.dcloud.service.event.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.sida.dcloud.service.event.ext.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseClientConfig {
    @Autowired
    private ActorSystem actorSystem;
    @Autowired
    private SpringExtension springExtension;

    public ActorRef actorRef(String actorName) {
        //现阶段未对akka消息持久化和消息保证机制进行处理，留到下一阶段完善
        return actorSystem.actorOf(springExtension.props(actorName), actorName);
    }
}
