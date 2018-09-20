package com.sida.dcloud.xdomain.common;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class SpringBeansManager {
    private static final Logger LOG = LoggerFactory.getLogger(SpringBeansManager.class);

    private static SpringBeansManager instance = new SpringBeansManager();

    public static SpringBeansManager getInstance() {
        return instance;
    }

    private ApplicationContext applicationContext;
//    private Map<String, Object> beansMap = new HashMap<>();

    private SpringBeansManager() {}

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void init() {
//        String[] array = applicationContext.getBeanDefinitionNames();
//        Arrays.stream(array).forEach(name -> beansMap.put(name, applicationContext.getBean(name)));
    }

    public Object getServiceBean(String poClassName) {
        String name = String.format("%sServiceImpl", WordUtils.uncapitalize(poClassName));
        return applicationContext.getBean(name);
    }

    public Object geMapperBean(String poClassName) {
        String name = String.format("%sMapper", WordUtils.uncapitalize(poClassName));
        return applicationContext.getBean(name);
    }

    public Object geDispatcherBean(String poClassName) {
        String name = String.format("%sDispatcher", WordUtils.uncapitalize(poClassName));
        return applicationContext.getBean(name);
    }

    public Object geActorBean(String poClassName) {
        String name = String.format("%sActor", WordUtils.uncapitalize(poClassName));
        return applicationContext.getBean(name);
    }
}
