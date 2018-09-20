package com.sida.dcloud.service.event.config;

import com.sida.xiruo.xframework.common.Contants;

public interface EventConstants extends Contants {
    int DEFAULT_EVENT_TIMEOUT = 3;

    String BACKEND_ACTOR_NAME = "eventBackendActor";
    String FRONTEND_ACTOR_NAME = "eventFrontendActor";
    String AKKA_CLUSTER_NAME = "EventActorSystem";
    String DEFAULT_CLIENT_CONFIG_NAME = "applicationAkka_client";
//    String[] AKKA_CLUSTER_PORTS = {"2551", "2552", "2553", "0"};
}
