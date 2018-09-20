package com.sida.dcloud.training.model;

import com.sida.dcloud.training.po.ChapterSection;
import com.sida.dcloud.training.uow.ChapterSectionUnitOfWork;
import com.sida.dcloud.xdomain.model.AggregateRoot;

public class ChapterSectionModel extends AggregateRoot<ChapterSection> {
    public static void increaseTotal(ChapterSectionModel model) {
        ChapterSectionUnitOfWork uow = (ChapterSectionUnitOfWork)model.getUnitOfWork();
        uow.increaseTotal(model);
    }

    public ChapterSectionModel(ChapterSection po) {
        super(po);
    }
}
