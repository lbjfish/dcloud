package com.sida.dcloud.training.uow;

import com.sida.dcloud.training.model.SpecialGroupModel;
import com.sida.dcloud.training.service.SpecialGroupService;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;

public class SpecialGroupUnitOfWork extends MybatisUnitOfWork<SpecialGroupModel> {
    public void increaseTotal(SpecialGroupModel model) {
        ((SpecialGroupService)getService()).increaseTotal(model.getPo());
    }
}
