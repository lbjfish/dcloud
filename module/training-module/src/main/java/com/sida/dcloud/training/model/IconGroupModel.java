package com.sida.dcloud.training.model;

import com.sida.dcloud.training.po.IconGroup;
import com.sida.dcloud.training.uow.IconGroupUnitOfWork;
import com.sida.dcloud.xdomain.model.AggregateRoot;

public class IconGroupModel extends AggregateRoot<IconGroup> {
    public static void increaseTotal(IconGroupModel model) {
        IconGroupUnitOfWork uow = (IconGroupUnitOfWork)model.getUnitOfWork();
        uow.increaseTotal(model);
    }

    public IconGroupModel(IconGroup po) {
        super(po);
    }
}
