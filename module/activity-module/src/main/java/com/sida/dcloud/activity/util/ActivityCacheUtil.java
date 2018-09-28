package com.sida.dcloud.activity.util;

import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.service.ActivitySignupNoteSettingService;
import com.sida.dcloud.activity.service.ActivitySignupNoteVersionService;
import com.sida.dcloud.event.po.activity.*;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(1)
public class ActivityCacheUtil implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityCacheUtil.class);
    private static String CACHE_KEY_CURRENT_VERSION_SETTING = "CACHE_IN_REDIS_FOR_ACTIVITY_MODULE_CURRENT_VERSION_SETTING";
    private static String CACHE_KEY_CURRENT_VERSION = "CACHE_IN_REDIS_FOR_ACTIVITY_MODULE_CURRENT_VERSION";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ActivitySignupNoteVersionService activitySignupNoteVersionService;
    @Autowired
    private ActivitySignupNoteSettingService activitySignupNoteSettingService;



    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @Override
    public void run(String... strings) throws Exception {
//        char[] array = new char[100];
//        Arrays.fill(array, '*');
//        LOG.info(new String(array));
        initCurrentVersionSettingCacheInRedis(false);
    }

    public void initCurrentVersionSettingCacheInRedis(boolean forceReload) {
        if(forceReload || redisUtil.getEntriesFromMap(CACHE_KEY_CURRENT_VERSION_SETTING) == null) {
            Map<String, ActivitySignupNoteSetting> poMap = new HashMap<>();
            redisUtil.putInMap(CACHE_KEY_CURRENT_VERSION_SETTING, poMap);
            String version = activitySignupNoteVersionService.getCurrentUsedVerion();
            if(null != version) {
                LOG.info("开始处理当前活动报名规则缓存");
                long start = System.currentTimeMillis() / 1000;
                List<ActivitySignupNoteSetting> poList = activitySignupNoteSettingService.selectByVersion(version);
                if (!poList.isEmpty()) {
                    poList.forEach(po -> poMap.put(po.getCode(), po));
                    redisUtil.putInMap(CACHE_KEY_CURRENT_VERSION_SETTING, poMap);
                    redisUtil.set(CACHE_KEY_CURRENT_VERSION, version);
                    LOG.info("处理当前活动报名规则缓存完成，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
                } else {
                    LOG.info("当前活动报名规则查无数据，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
                }
            } else {
                LOG.warn("没有找到活动报名规则");
            }
        }
    }

    public void updatePartCurrentVersionSettingCacheInRedis(ActivitySignupNoteSetting po, ActivityEventType eventType) {
        Map<String, ActivitySignupNoteSetting> poMap = Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_CURRENT_VERSION_SETTING)).orElse(new HashMap<>());
        switch(eventType) {
            case ACTIVITY_EVENT_ACTIVITY_SIGNUP_NOTE_SETTING_INSERT:
            case ACTIVITY_EVENT_ACTIVITY_SIGNUP_NOTE_SETTING_UPDATE:
                poMap.put(po.getCode(), po);
                break;
            case ACTIVITY_EVENT_ACTIVITY_SIGNUP_NOTE_SETTING_REMOVE:
//                Arrays.stream(po.getStringIds().split(",")).forEach(id -> poMap.remove(id));
                String idString = "," + po.getStringIds() + ",";
//                poMap.values().stream().filter(o -> idString.indexOf(String.format(",%s,", o.getId())) >= 0).forEach(poMap::remove);
                poMap.values().stream().filter(o -> idString.indexOf(String.format(",%s,", o.getId())) >= 0)
                        .map(o -> o.getCode()).collect(Collectors.toList()).forEach(poMap::remove);
                break;
        }
        redisUtil.putInMap(CACHE_KEY_CURRENT_VERSION_SETTING, poMap);
    }

    public Map<String, ActivitySignupNoteSetting> getVersionSettingMap() {
        return Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_CURRENT_VERSION_SETTING)).orElse(new HashMap<>());
    }

    public String getCurrentVersion() {
        return (String)Optional.ofNullable(redisUtil.get(CACHE_KEY_CURRENT_VERSION)).orElse("");
    }
}
