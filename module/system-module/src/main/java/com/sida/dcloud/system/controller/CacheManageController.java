package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.cache.CacheLoader;
import com.sida.xiruo.util.jedis.RedisKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangbaidong
 * 2017/10/31.
 */
@Api(description = "缓存管理")
@RestController
@RequestMapping("cacheManage")
public class CacheManageController {

    @Autowired
    private CacheLoader cacheLoader;

    @GetMapping("reloadDictCache")
    @ApiOperation("重新加载数据字典缓存")
    public void reloadDictCache() {
        cacheLoader.reloadRedisCache(RedisKey.DICT_GROUP);
        cacheLoader.reloadRedisCache(RedisKey.DICT_DATA);
    }

    @GetMapping("reloadCacheVersion")
    @ApiOperation("重新加载缓存版本")
    public void reloadCacheVersion() {
        cacheLoader.reloadRedisCache(RedisKey.CACHE_VERSION);
    }

    @GetMapping("reloadOrgCache")
    @ApiOperation("重新加载组织机构缓存")
    public void reloadOrgCache() {
        cacheLoader.reloadRedisCache(RedisKey.ORG_MAP);
    }

}
