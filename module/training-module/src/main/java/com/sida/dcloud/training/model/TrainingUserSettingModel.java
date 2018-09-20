package com.sida.dcloud.training.model;

import com.sida.dcloud.training.po.TrainingUserSetting;
import com.sida.dcloud.training.uow.TrainingUserSettingUnitOfWork;
import com.sida.dcloud.xdomain.model.DomainEntity;

public class TrainingUserSettingModel extends DomainEntity<TrainingUserSetting> {

    public TrainingUserSettingModel(TrainingUserSetting po) {
        super(po);
    }

    public static void save(TrainingUserSettingModel model) {
        ((TrainingUserSettingUnitOfWork)model.getUnitOfWork()).save(model);
    }
}
