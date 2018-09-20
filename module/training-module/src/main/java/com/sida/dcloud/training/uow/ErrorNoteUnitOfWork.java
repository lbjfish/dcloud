package com.sida.dcloud.training.uow;

import com.sida.dcloud.training.model.ErrorNoteModel;
import com.sida.dcloud.training.service.ErrorNoteService;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;

public class ErrorNoteUnitOfWork extends MybatisUnitOfWork<ErrorNoteModel> {
    @Override
    public void updateDirty() {
        ErrorNoteService errorNoteService = (ErrorNoteService)getService();
        dirtyEntities.forEach(entity -> {
            errorNoteService.updateMaybeCorrect(entity.getPo());
        });
    }
}
