package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dao.ActivitySignupNoteVersionMapper;
import com.sida.dcloud.activity.po.ActivitySignupNoteVersion;
import com.sida.dcloud.activity.service.ActivitySignupNoteVersionService;
import com.sida.dcloud.activity.util.ActivityCacheUtil;
import com.sida.dcloud.activity.vo.CustomerActivitySignupNoteVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActivitySignupNoteVersionServiceImpl extends BaseServiceImpl<ActivitySignupNoteVersion> implements ActivitySignupNoteVersionService {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitySignupNoteVersionServiceImpl.class);

    @Autowired
    private ActivitySignupNoteVersionMapper activitySignupNoteVersionMapper;
    @Autowired
    private ActivityCacheUtil activityCacheUtil;

    @Override
    public IMybatisDao<ActivitySignupNoteVersion> getBaseDao() {
        return activitySignupNoteVersionMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @RedisLock
    @Override
    public int insertVersion(String version) {
        ActivitySignupNoteVersion po = new ActivitySignupNoteVersion();
        po.setVersion(version);
        po.setCurrentUsed(false);
        po.setVersionTime(new Date());
        po.setDeleteFlag(false);
        int result = activitySignupNoteVersionMapper.insertVersion(po);
        changeCurrentVersion(version);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void changeCurrentVersion(String version) {
        activitySignupNoteVersionMapper.setCurrentUsedVersion(version);
        activityCacheUtil.initCurrentVersionSettingCacheInRedis(true);
    }

    @Override
    public int deleteByVersion(String version) {
        if(activityCacheUtil.getCurrentVersion().equals(version)) {
            throw new ActivityException("当前正在使用的版本不允许删除");
        }
        return activitySignupNoteVersionMapper.deleteByVersion(version);
    }

    @Override
    public int resumeByVersion(String version) {
        return activitySignupNoteVersionMapper.resumeByVersion(version);
    }

    @Override
    public String getCurrentUsedVerion() {
        return Optional.ofNullable(activitySignupNoteVersionMapper.getCurrentUsedVerion()).orElse(new ActivitySignupNoteVersion()).getVersion();
    }

    @Override
    public ActivitySignupNoteVersion selectByVerion(String version) {
        return activitySignupNoteVersionMapper.selectByVerion(version);
    }

    @Override
    public Page<ActivitySignupNoteVersion> findPageList(ActivitySignupNoteVersion po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<ActivitySignupNoteVersion> voList = activitySignupNoteVersionMapper.selectByCondition(po);
        return (Page) voList;
    }
}