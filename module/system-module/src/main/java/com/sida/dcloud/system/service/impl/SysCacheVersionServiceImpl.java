package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysCacheVersionMapper;
import com.sida.dcloud.system.po.SysCacheVersion;
import com.sida.dcloud.system.service.SysCacheVersionService;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysCacheVersionServiceImpl extends BaseServiceImpl<SysCacheVersion> implements SysCacheVersionService {
    private static final Logger logger = LoggerFactory.getLogger(SysCacheVersionServiceImpl.class);

    @Autowired
    private SysCacheVersionMapper sysCacheVersionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IMybatisDao<SysCacheVersion> getBaseDao() {
        return sysCacheVersionMapper;
    }

    @Override
    public SysCacheVersion selectByRedisKey(String redisKey) {
        SysCacheVersion version = new SysCacheVersion();
        version.setRedisKey(redisKey);
        List<SysCacheVersion> cacheVersions = sysCacheVersionMapper.selectByCondition(version);
        if(BlankUtil.isNotEmpty(cacheVersions)) {
            return cacheVersions.get(0);
        }
        return null;
    }

    @Override
    public List<SysCacheVersion> findAll() {
        return sysCacheVersionMapper.selectByCondition(null);
    }

    @Override
    public void updateCacheVersion(String redisKey) {
        //1、更新数据库缓存版本
        SysCacheVersion cacheVersion = selectByRedisKey(redisKey);
        if(cacheVersion==null) {
            cacheVersion = new SysCacheVersion();
        }
        String version = UUID.randomUUID().toString();
        cacheVersion.setLastUpdatedTime(new Date());
        cacheVersion.setRedisKey(redisKey);
        cacheVersion.setLastUpdatedVersion(version);
        saveOrUpdate(cacheVersion);

        //2、更新Redis缓存版本
        redisUtil.putInMap(RedisKey.CACHE_VERSION, redisKey, version);
    }

    public Map<String, String> findAllForCache() {
        List<SysCacheVersion> cacheVersions = findAll();
        if(BlankUtil.isNotEmpty(cacheVersions)) {
            Map<String, String> cacheVersionMap = new HashMap<>();
            for(SysCacheVersion version : cacheVersions) {
                cacheVersionMap.put(version.getRedisKey(), version.getLastUpdatedVersion());
            }
           return cacheVersionMap;
        }
        return null;
    }

    @Override
    public void loadCacheVersionToRedis() {
        Map<String, String> cache = findAllForCache();
        redisUtil.putInMap(RedisKey.CACHE_VERSION, cache);
    }
}