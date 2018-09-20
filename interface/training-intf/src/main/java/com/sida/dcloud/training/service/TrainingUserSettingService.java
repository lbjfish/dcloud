package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.TrainingUserSetting;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface TrainingUserSettingService extends IBaseService<TrainingUserSetting> {
    List<TrainingUserSetting> selectByUserId(String userId);
}