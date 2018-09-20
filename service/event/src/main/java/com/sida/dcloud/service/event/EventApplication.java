package com.sida.dcloud.service.event;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.dcloud.service.event.ext.SpringExtension;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//@SpringCloudApplication
@SpringBootApplication(scanBasePackages = {"com.sida.dcloud.service.event"})
public class EventApplication implements CommandLineRunner {
    private final static Logger LOG = LoggerFactory.getLogger(EventApplication.class.getName());

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(EventApplication.class).run(args);
    }

//    @Autowired
//    private ActorSystem actorSystem;
    @Autowired
    private SpringExtension springExtension;
    @Autowired
    private ApplicationContext applicationContext;

    @Value("${actor.akka.cluster.host}")String actorHost;
    @Value("${actor.akka.cluster.seed-nodes}")String seedNodes;

    @Override
    public void run(String... args) throws Exception {
        startup();
    }
//        final ActorRef actorRef = actorSystem.actorOf(springExtension.props("eventBackendActor"), EventConstants.BACKEND_ACTOR_NAME);


    private void startup() {
        springExtension.initialize(applicationContext);
        List<String> clusterList = new ArrayList<String>();
        String[] nodes = seedNodes.split(",");
        Arrays.stream(nodes).forEach(node -> {
            clusterList.add(String.format("akka.tcp://%s@%s", EventConstants.AKKA_CLUSTER_NAME, node));
        });
        List<String> nodeList = new ArrayList<String>(Arrays.asList(nodes));
        nodeList.add(String.format("%s:0", actorHost));
        nodeList.forEach(node -> {
            LOG.info("Node is {}", node);
            String[] hostAndPort = node.split(":");
            Config config = ConfigFactory.parseMap(new HashMap<String, Object>() {
                {
                    put("akka.remote.netty.tcp.hostname", hostAndPort[0]);
                    put("akka.remote.netty.tcp.port", hostAndPort[1]);
                    put("akka.cluster.seed-nodes", clusterList);
                }
            }).withFallback(ConfigFactory.load("applicationAkka_random"));
            ActorSystem actorSystem = ActorSystem.create("EventActorSystem", config);
//            actorSystem.actorOf(springExtension.props("eventBackendActor"), "eventBackendActor");
            //第一个eventBackendActor表示spring bean的名字，第二个eventBackendActor表示akka发布地址的后缀
            final ActorRef eventBackendActor = actorSystem.actorOf(springExtension.props("eventBackendActor"), EventConstants.BACKEND_ACTOR_NAME);
        });
        LOG.info("eventBackendActor service started...");
    }
}
