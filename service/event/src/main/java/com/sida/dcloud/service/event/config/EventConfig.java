package com.sida.dcloud.service.event.config;

import akka.actor.ActorSystem;
import com.sida.dcloud.service.event.ext.SpringExtension;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Configuration
public class EventConfig {
    private final static Logger LOG = LoggerFactory.getLogger(EventConfig.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private SpringExtension springExtension;
    @Value("${actor.akka.cluster.host}")String actorHost;
    @Value("${actor.akka.cluster.seed-nodes}")String seedNodes;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem actorSystem = ActorSystem.create(EventConstants.AKKA_CLUSTER_NAME, akkaConfiguration());
        springExtension.initialize(applicationContext);
        return actorSystem;
    }

    //    @Bean
    public Config akkaConfiguration() {
        List<String> clusterList = new ArrayList<String>();
        String[] nodes = seedNodes.split(",");
        Arrays.stream(nodes).forEach(node -> {
            clusterList.add(String.format("akka.tcp://%s@%s", EventConstants.AKKA_CLUSTER_NAME, node));
        });
        return ConfigFactory.parseMap(new HashMap<String, Object>() {
            {
                put("akka.remote.netty.tcp.hostname", actorHost);
                put("akka.remote.netty.tcp.port", "0");
                put("akka.cluster.seed-nodes", clusterList);
            }
        }).withFallback(ConfigFactory.load(getClientConfigName()));
    }

    protected String getClientConfigName() {
        return EventConstants.DEFAULT_CLIENT_CONFIG_NAME;
    }
}