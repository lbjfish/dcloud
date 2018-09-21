package com.sida.dcloud.activity.service;

import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ActivitySignupNoteSettingService extends IBaseService<ActivitySignupNoteSetting> {
    List<ActivitySignupNoteSetting> generateSetting(String version);
    List<ActivitySignupNoteSetting> selectByVersion(String version);
    int deleteByVersion(String version);
    int resumeByVersion(String version);
    int updateSettingList(List<ActivitySignupNoteSetting> settingList);
}