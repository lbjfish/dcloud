package com.sida.dcloud.training.uow;

import com.sida.dcloud.training.model.IconGroupModel;
import com.sida.dcloud.training.service.IconGroupService;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;

public class IconGroupUnitOfWork extends MybatisUnitOfWork<IconGroupModel> {
    public void increaseTotal(IconGroupModel model) {
        ((IconGroupService)getService()).increaseTotal(model.getPo());
    }
}
