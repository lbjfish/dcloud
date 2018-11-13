package com.sida.dcloud.activity.util;

import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.service.ActivityOrderService;
import com.sida.dcloud.activity.service.ActivitySignupNoteSettingService;
import com.sida.dcloud.activity.service.ActivitySignupNoteVersionService;
import com.sida.dcloud.activity.service.CustomerActivitySignupNoteService;
import com.sida.dcloud.activity.service.impl.ActivityOrderServiceImpl;
import com.sida.dcloud.activity.service.impl.CustomerActivitySignupNoteServiceImpl;
import com.sida.dcloud.event.po.activity.*;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.cache.redis.XiruoRedisAtomicLong;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(1)
public class ActivityCacheUtil implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityCacheUtil.class);
    private static final String CACHE_KEY_CURRENT_VERSION_SETTING = "CACHE_IN_REDIS_FOR_ACTIVITY_MODULE_CURRENT_VERSION_SETTING";
    private static final String CACHE_KEY_CURRENT_VERSION = "CACHE_IN_REDIS_FOR_ACTIVITY_MODULE_CURRENT_VERSION";
    private static final String ACTION_NO_TEMPLATE = "ACTIVITY_%s_%s_%s";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ActivitySignupNoteVersionService activitySignupNoteVersionService;
    @Autowired
    private ActivitySignupNoteSettingService activitySignupNoteSettingService;
    @Autowired
    private CustomerActivitySignupNoteService customerActivitySignupNoteService;
    @Autowired
    private ActivityOrderService activityOrderService;


    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @Override
    public void run(String... strings) throws Exception {
//        char[] array = new char[100];
//        Arrays.fill(array, '*');
//        LOG.info(new String(array));
        initCurrentVersionSettingCacheInRedis(false);
        initActionNos();
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

    /**
     * 初始化操作编号
     */
    private void initActionNos() {
        initSignupActionNo();
        initOrderActionNo();
        initSignupThirdPartCode();
    }

    private void initSignupThirdPartCode() {
        RedisAtomicLong actionNoCounter = new RedisAtomicLong(CustomerActivitySignupNoteServiceImpl.THIRD_PART_CODE_KEY, redisUtil.getRedisTemplate().getConnectionFactory());
        if(actionNoCounter== null ||actionNoCounter.get() == 0) {
            actionNoCounter.set(System.currentTimeMillis() / 1000);
        }
    }

    private void initSignupActionNo() {
        XiruoRedisAtomicLong actionNoCounter = new XiruoRedisAtomicLong(CustomerActivitySignupNoteServiceImpl.ACTION_NO_KEY, redisUtil.getRedisTemplate().getConnectionFactory(), Xiruo.TIMEPOINT.DAY);
        if(actionNoCounter== null ||actionNoCounter.get() == 0) {
            String actionNo = customerActivitySignupNoteService.getCurrentNoteNo();
            if(!actionNo.equals("0")) {
                int lastIndex = actionNo.lastIndexOf("_");
                actionNoCounter.set(Xiruo.nullToLong(actionNo.substring(lastIndex + 1)) + 1);
            }
        }
    }

    private void initOrderActionNo() {
        XiruoRedisAtomicLong actionNoCounter = new XiruoRedisAtomicLong(ActivityOrderServiceImpl.ACTION_NO_KEY, redisUtil.getRedisTemplate().getConnectionFactory(), Xiruo.TIMEPOINT.DAY);
        if(actionNoCounter== null ||actionNoCounter.get() == 0) {
            String actionNo = activityOrderService.getCurrentOrderNo();
            if(!actionNo.equals("0")) {
                int lastIndex = actionNo.lastIndexOf("_");
                actionNoCounter.set(Xiruo.nullToLong(actionNo.substring(lastIndex + 1)) + 1);
            }
        }
    }

    /**
     * 根据key获取操作编号
     * @param key
     * @return
     */
    public String getActionNoByKey(String key) {
        XiruoRedisAtomicLong actionNoCounter = new XiruoRedisAtomicLong(key, redisUtil.getRedisTemplate().getConnectionFactory(), Xiruo.TIMEPOINT.DAY);
        long counter = actionNoCounter.getAndIncrement();
        return String.format(ACTION_NO_TEMPLATE
                , key.toUpperCase()
                , new SimpleDateFormat("yyyyMMdd").format(new Date())
                , new DecimalFormat("0000000000").format(counter));
    }

    /**
     *
     * @return
     */
    public String getThirdPartCode() {
        RedisAtomicLong actionNoCounter = new RedisAtomicLong(CustomerActivitySignupNoteServiceImpl.THIRD_PART_CODE_KEY, redisUtil.getRedisTemplate().getConnectionFactory());
        long counter = actionNoCounter.getAndIncrement();
        return new DecimalFormat("0000000000").format(counter);
    }
}
