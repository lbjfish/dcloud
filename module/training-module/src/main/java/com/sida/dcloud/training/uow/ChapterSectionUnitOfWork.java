package com.sida.dcloud.training.uow;

import com.sida.dcloud.training.model.ChapterSectionModel;
import com.sida.dcloud.training.service.ChapterSectionService;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;

public class ChapterSectionUnitOfWork extends MybatisUnitOfWork<ChapterSectionModel> {
    public void increaseTotal(ChapterSectionModel model) {
        ((ChapterSectionService)getService()).increaseTotal(model.getPo());
    }
}
