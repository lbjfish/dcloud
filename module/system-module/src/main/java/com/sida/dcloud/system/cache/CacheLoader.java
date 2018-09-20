package com.sida.dcloud.system.cache;

import com.sida.dcloud.system.po.SysCacheVersion;
import com.sida.dcloud.system.service.*;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * Created by huangbaidong
 * 2017/10/26.
 */
@Component
public class CacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(CacheLoader.class);

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private SysCacheVersionService sysCacheVersionService;

    @Autowired
    private SysDictionaryDataService sysDictionaryDataService;

    @Autowired
    private SysDictionaryGroupService sysDictionaryGroupService;

    @Autowired
    private SysGlobalVariableService sysGlobalVariableService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 加载Redis缓存
     */
    public void loadRedisCache() {

        //1、获取database最新缓存版本
        List<SysCacheVersion> dbVersions = sysCacheVersionService.findAll();
        //2、获取Redis缓存版本
        Map<String, String> redisVersions = redisUtil.getCacheVersion();
        //3、比较Redis与database的差异，如果有差异就重新加载缓存
        if(BlankUtil.isNotEmpty(dbVersions)) {
            Map<String, String> newRedisVersion = new HashMap<>();
            for(SysCacheVersion dbVersion : dbVersions) {
                String redisValue = redisVersions.get(dbVersion.getRedisKey());
                if(BlankUtil.isEmpty(redisValue) ||
                        !redisValue.equals(dbVersion.getLastUpdatedVersion())) {
                    loadData(dbVersion.getRedisKey());
                }
                newRedisVersion.put(dbVersion.getRedisKey(), dbVersion.getLastUpdatedVersion());
            }
            //4、更新redis版本与database保持一致
            redisUtil.putInMap(RedisKey.CACHE_VERSION, newRedisVersion);
        }

    }

    /**
     * 加载业务数据到缓存
     * @param redisKey
     */
    private void loadData(String redisKey) {
        switch (redisKey) {
            case RedisKey.DICT_GROUP:
                logger.error("=============>加载数据字典分组");
                sysDictionaryGroupService.loadDictGroupsToRedis();
                break;
            case RedisKey.DICT_DATA:
                logger.error("=============>加载数据字典");
                sysDictionaryDataService.loadDictsToRedis();
                break;
            case RedisKey.ORG_MAP:
                logger.error("=============>加载组织架构");
                sysOrgService.loadOrgsToRedis();
                break;
            case RedisKey.CACHE_VERSION:
                logger.error("=============>加载缓存版本");
                sysCacheVersionService.loadCacheVersionToRedis();
                break;
            case RedisKey.GLOBAL_VARIABLE:
                logger.error("=============>加载全局配置表");
                sysGlobalVariableService.loadDatasToRedis();
                break;
            default:
                logger.error("=============>不存在的缓存键："+redisKey);
                System.err.println("=============>不存在的缓存键："+redisKey);
        }
    }

    public void reloadRedisCache(String redisKey) {
        loadData(redisKey);
    }
}
