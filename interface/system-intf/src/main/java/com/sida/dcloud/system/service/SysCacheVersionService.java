package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysCacheVersion;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface SysCacheVersionService extends IBaseService<SysCacheVersion> {

    SysCacheVersion selectByRedisKey(String redisKey);

    List<SysCacheVersion> findAll();

    void updateCacheVersion(String redisKey);

    Map<String, String> findAllForCache();

    void loadCacheVersionToRedis();

}