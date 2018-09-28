package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.ActivitySignupNoteSettingMapper;
import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.service.ActivitySignupNoteSettingService;
import com.sida.dcloud.activity.service.ActivitySignupNoteVersionService;
import com.sida.dcloud.activity.util.ActivityCacheUtil;
import com.sida.dcloud.activity.util.ActivitySignupNoteSettingGenerator;
import com.sida.dcloud.event.po.activity.ActivityEventType;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivitySignupNoteSettingServiceImpl extends BaseServiceImpl<ActivitySignupNoteSetting> implements ActivitySignupNoteSettingService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitySignupNoteSettingServiceImpl.class);

    @Autowired
    private ActivitySignupNoteSettingMapper activitySignupNoteSettingMapper;
    @Autowired
    private ActivitySignupNoteVersionService activitySignupNoteVersionService;
    @Autowired
    private ActivitySignupNoteSettingGenerator activitySignupNoteSettingGenerator;
    @Autowired
    private ActivityCacheUtil activityCacheUtil;

    @Override
    public IMybatisDao<ActivitySignupNoteSetting> getBaseDao() {
        return activitySignupNoteSettingMapper;
    }

    @RedisLock
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ActivitySignupNoteSetting> generateSetting(String version) {
        activitySignupNoteSettingMapper.resumeByVersion(version);
        List<ActivitySignupNoteSetting> settingList = selectByVersion(version);
        if(settingList.isEmpty()) {
            settingList = activitySignupNoteSettingGenerator.generate(version);
            activitySignupNoteSettingMapper.batchInsert(settingList);
            activitySignupNoteVersionService.insertVersion(version);
        } else {
            LOG.warn("给定的版本设置已经存在，不再重新产生，直接返回。version = {}", version);
        }
        return settingList;
    }

    @Override
    public List<ActivitySignupNoteSetting> selectByVersion(String version) {
        return activitySignupNoteSettingMapper.selectByVersion(version);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @RedisLock
    @Override
    public int deleteByVersion(String version) {
        activitySignupNoteVersionService.deleteByVersion(version);
        return activitySignupNoteSettingMapper.deleteByVersion(version);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @RedisLock
    @Override
    public int resumeByVersion(String version) {
        activitySignupNoteVersionService.resumeByVersion(version);
        return activitySignupNoteSettingMapper.resumeByVersion(version);
    }

    @RedisLock
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateSettingList(List<ActivitySignupNoteSetting> settingList) {
        settingList.forEach(setting -> {
            activitySignupNoteSettingMapper.updateByPrimaryKey(setting);
            activityCacheUtil.updatePartCurrentVersionSettingCacheInRedis(setting, ActivityEventType.ACTIVITY_EVENT_ACTIVITY_SIGNUP_NOTE_SETTING_UPDATE);
        });
        return 0;
    }
}