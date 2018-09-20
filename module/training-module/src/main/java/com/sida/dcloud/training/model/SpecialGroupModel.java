package com.sida.dcloud.training.model;

import com.sida.dcloud.training.po.SpecialGroup;
import com.sida.dcloud.training.uow.SpecialGroupUnitOfWork;
import com.sida.dcloud.xdomain.model.AggregateRoot;

public class SpecialGroupModel extends AggregateRoot<SpecialGroup> {
    public static void increaseTotal(SpecialGroupModel model) {
        SpecialGroupUnitOfWork uow = (SpecialGroupUnitOfWork)model.getUnitOfWork();
        uow.increaseTotal(model);
    }

    public SpecialGroupModel(SpecialGroup po) {
        super(po);
    }
}
