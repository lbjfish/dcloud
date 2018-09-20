package com.sida.dcloud.system.init;

import com.sida.dcloud.system.cache.CacheLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 系统模块初始化处理类
 * created by huangbaidong
 * 2017-10-21 17:30
 */
@Component
public class SystemApplicationInitializationListener implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private CacheLoader cacheLoader;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.err.println("------------------>系统模块初始化开始");
		cacheLoader.loadRedisCache();
		System.err.println("------------------>系统模块初始化结束");
	}

}