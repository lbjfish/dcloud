package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dao.TrainingUserSettingMapper;
import com.sida.dcloud.training.po.TrainingUserSetting;
import com.sida.dcloud.training.service.TrainingUserSettingService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingUserSettingServiceImpl extends BaseServiceImpl<TrainingUserSetting> implements TrainingUserSettingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingUserSettingServiceImpl.class);

    @Autowired
    private TrainingUserSettingMapper trainingUserSettingMapper;

    @Override
    public IMybatisDao<TrainingUserSetting> getBaseDao() {
        return trainingUserSettingMapper;
    }

    @Override
    public List<TrainingUserSetting> selectByUserId(String userId) {
        return trainingUserSettingMapper.selectByUserId(userId);
    }
}