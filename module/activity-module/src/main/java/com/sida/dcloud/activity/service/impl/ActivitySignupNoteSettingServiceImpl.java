package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.client.OperationClient;
import com.sida.dcloud.activity.dao.ActivitySignupNoteSettingMapper;
import com.sida.dcloud.activity.dto.ActivitySignupNoteSettingDto;
import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.service.ActivitySignupNoteSettingService;
import com.sida.dcloud.activity.service.ActivitySignupNoteVersionService;
import com.sida.dcloud.activity.util.ActivityCacheUtil;
import com.sida.dcloud.activity.util.ActivitySignupNoteSettingGenerator;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.dcloud.event.po.activity.ActivityEventType;
import com.sida.xiruo.xframework.controller.LoginManager;
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
import java.util.Map;
import java.util.Optional;

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
    @Autowired
    private OperationClient operationClient;

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

    @Override
    public List<ActivitySignupNoteSettingDto> selectByVersionToClient(String version) {
        version = Optional.ofNullable(version).orElse(activitySignupNoteVersionService.getCurrentUsedVerion());
        List<ActivitySignupNoteSettingDto> dtoList = activitySignupNoteSettingMapper.selectByVersionToClient(version);
        SysUserVo user = LoginManager.getUser();
        if(!user.isIncludeCustomerInfo()) {
            Map<String, String> map = (Map<String, String>) operationClient.findCommonUserById(user.getId());
            LoginManager.updateUserForCustomer(user1 -> {
                user1.setOwnerUrl(map.get("ownerUrl"));
                user1.setFaceUrl(map.get("faceUrl"));
                user1.setCardtype(map.get("cardtype"));
                user1.setIdcard(map.get("idcard"));
                user1.setPhone(map.get("phone"));
                user1.setAddress(map.get("address"));
                user1.setPostcode(map.get("postcode"));
                user1.setRegionId(map.get("regionId"));
                user1.setNationality(map.get("nationality"));
                user1.setHomepage(map.get("homepage"));
                user1.setIntroduce(map.get("introduce"));
                user1.setSinaWeibo(map.get("sinaWeibo"));
                user1.setAlipayNo(map.get("alipayNo"));
                user1.setFacebookNo(map.get("facebookNo"));
                user1.setTwitterNo(map.get("twitterNo"));
            });
        }
        dtoList.forEach(dto -> {
            Optional.ofNullable(dto.getCode()).filter("sex"::equals).ifPresent(code -> dto.setValue(user.getSex()));
            Optional.ofNullable(dto.getCode()).filter("address"::equals).ifPresent(code -> dto.setValue(user.getAddress()));
            Optional.ofNullable(dto.getCode()).filter("postCode"::equals).ifPresent(code -> dto.setValue(user.getPostcode()));
            Optional.ofNullable(dto.getCode()).filter("mobile1No"::equals).ifPresent(code -> dto.setValue(user.getMobile()));
            Optional.ofNullable(dto.getCode()).filter("email"::equals).ifPresent(code -> dto.setValue(user.getEmail()));
            Optional.ofNullable(dto.getCode()).filter("wechat"::equals).ifPresent(code -> dto.setValue(user.getWechat()));
            Optional.ofNullable(dto.getCode()).filter("qq"::equals).ifPresent(code -> dto.setValue(user.getQq()));
            Optional.ofNullable(dto.getCode()).filter("regionId"::equals).ifPresent(code -> dto.setValue(user.getRegionId()));
            Optional.ofNullable(dto.getCode()).filter("idType"::equals).ifPresent(code -> dto.setValue(user.getIdType()));
            Optional.ofNullable(dto.getCode()).filter("idNo"::equals).ifPresent(code -> dto.setValue(user.getIdcard()));
            Optional.ofNullable(dto.getCode()).filter("name"::equals).ifPresent(code -> dto.setValue(user.getName()));
            Optional.ofNullable(dto.getCode()).filter("userId"::equals).ifPresent(code -> dto.setValue(user.getId()));
        });
        return dtoList;
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