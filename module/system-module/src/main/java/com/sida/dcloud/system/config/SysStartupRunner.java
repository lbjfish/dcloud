package com.sida.dcloud.system.config;

import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.dcloud.system.service.SysResourceService;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * spring boot 启动加载数据
 */
@Component
public class SysStartupRunner implements CommandLineRunner {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysResourceService sysResourceService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
        System.out.println("");
        List<SysResourceVo> list = sysResourceService.findListByRoleCode(null,null);
        redisUtil.set(RedisKey.RESOURCE_ALL, com.alibaba.fastjson.JSON.toJSONString(list));
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作完成<<<<<<<<<<<<<");
    }

}
