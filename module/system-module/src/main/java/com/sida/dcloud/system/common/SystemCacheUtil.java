package com.sida.dcloud.system.common;

import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.dcloud.system.dto.SysRegionLayerDto;
import com.sida.dcloud.system.service.SysRegionService;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.util.BuildTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Order(1)
public class SystemCacheUtil implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(SystemCacheUtil.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysRegionService sysRegionService;

    @Override
    public void run(String... args) throws Exception {
        initRegionDatasToRedis();
    }

    /**
     *
     */
    @RedisLock
    private Map<String, Object> initRegionDatasToRedis() {
        Map<String, Object> map = redisUtil.getEntriesFromMap(RedisKey.SYS_REGION_CACHE);
        if(map == null) {
            map = new HashMap<>();
            System.out.println(">>>>>>>>>>>>>>>初始化sys_region，执行加载数据等操作<<<<<<<<<<<<<");
            List<SysRegionLayerDto> countryList = sysRegionService.findSysRegionSingleLayerDtoByLevel("COUNTRY");
            List<SysRegionLayerDto> provinceList = sysRegionService.findSysRegionSingleLayerDtoByLevel("PROVINCE");
            List<SysRegionLayerDto> cityList = sysRegionService.findSysRegionSingleLayerDtoByLevel("CITY");
            List<SysRegionLayerDto> list = new ArrayList<>(countryList);
            list.addAll(provinceList);
            list.addAll(cityList);
            List<SysRegionLayerDto> layerList = BuildTree.buildTree(list);
            map.put(RedisKey.SYS_REGION_CACHE_WITH_CITY, cityList);
            map.put(RedisKey.SYS_REGION_CACHE_WITH_PROVINCE, provinceList);
            map.put(RedisKey.SYS_REGION_CACHE_WITH_COUNTRY, countryList);
            map.put(RedisKey.SYS_REGION_CACHE_WITH_THREE_LEVEL_BY_LAYER, layerList);
            Map<String, SysRegionLayerDto> flatMap = new HashMap<>();
            list.forEach(dto -> flatMap.put(dto.getId(), dto));
            map.put(RedisKey.SYS_REGION_CACHE_WITH_ALL_BY_FLAT, layerList);
            redisUtil.putInMap(RedisKey.SYS_REGION_CACHE, map);
            System.out.println(">>>>>>>>>>>>>>>初始化sys_region完成<<<<<<<<<<<<<");
        }
        return map;
    }

    public void clearRegionDatasInRedis() {
        redisUtil.remove(RedisKey.SYS_REGION_CACHE);
    }

    public Object getRegionDataByKey(String key) {
        return Optional.ofNullable(redisUtil.getRegionDatasByKey(key))
                .orElse(initRegionDatasToRedis().get(key));
    }
}
