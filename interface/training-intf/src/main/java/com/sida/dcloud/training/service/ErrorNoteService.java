package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.ErrorNote;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ErrorNoteService extends IBaseService<ErrorNote> {
    List<ErrorNote> selectByUserWithCorrected(ErrorNote po);
    void physicalDeleteByPrimaryKey(String id);
    void physicalDeleteByPrimaryKeys(String stringIds);
    void deleteByExerciseIdAndUserId(ErrorNote po);
    void physicalDeleteByExerciseIdAndUserId(ErrorNote po);
    void updateMaybeCorrect(ErrorNote po);
}