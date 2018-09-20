package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dao.ErrorNoteMapper;
import com.sida.dcloud.training.po.ErrorNote;
import com.sida.dcloud.training.service.ErrorNoteService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorNoteServiceImpl extends BaseServiceImpl<ErrorNote> implements ErrorNoteService {
    private static final Logger logger = LoggerFactory.getLogger(ErrorNoteServiceImpl.class);

    @Autowired
    private ErrorNoteMapper errorNoteMapper;

    @Override
    public IMybatisDao<ErrorNote> getBaseDao() {
        return errorNoteMapper;
    }

    @Override
    public List<ErrorNote> selectByUserWithCorrected(ErrorNote po) {
        return errorNoteMapper.selectByUserWithCorrected(po);
    }

    @Override
    public void physicalDeleteByPrimaryKey(String id) {
        errorNoteMapper.physicalDeleteByPrimaryKey(id);
    }

    @Override
    public void physicalDeleteByPrimaryKeys(String stringIds) {
        errorNoteMapper.physicalDeleteByPrimaryKeys(stringIds.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", "'"));
    }

    @Override
    public void deleteByExerciseIdAndUserId(ErrorNote po) {
        errorNoteMapper.deleteByExerciseIdAndUserId(po);
    }

    @Override
    public void physicalDeleteByExerciseIdAndUserId(ErrorNote po) {
        errorNoteMapper.physicalDeleteByExerciseIdAndUserId(po);
    }

    @Override
    public void updateMaybeCorrect(ErrorNote po) {
        errorNoteMapper.updateMaybeCorrect(po);
    }
}