package com.sida.dcloud.training.uow;

import com.sida.dcloud.training.model.TrainingUserSettingModel;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;

public class TrainingUserSettingUnitOfWork extends MybatisUnitOfWork<TrainingUserSettingModel> {
    public void save(TrainingUserSettingModel model) {
        getService().saveOrUpdate(model.getPo());
    }
}