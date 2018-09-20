package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dao.ExamAnswerTrackMapper;
import com.sida.dcloud.training.po.ExamAnswerTrack;
import com.sida.dcloud.training.service.ExamAnswerTrackService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamAnswerTrackServiceImpl extends BaseServiceImpl<ExamAnswerTrack> implements ExamAnswerTrackService {
    private static final Logger logger = LoggerFactory.getLogger(ExamAnswerTrackServiceImpl.class);

    @Autowired
    private ExamAnswerTrackMapper examAnswerTrackMapper;

    @Override
    public IMybatisDao<ExamAnswerTrack> getBaseDao() {
        return examAnswerTrackMapper;
    }

    @Override
    public List<ExamAnswerTrack> selectByExamId(String examId) {
        return examAnswerTrackMapper.selectByExamId(examId);
    }

    @Override
    public void physicalDeleteByExamId(String examId) {
        examAnswerTrackMapper.physicalDeleteByExamId(examId);
    }

    @Override
    public void deleteByExamId(String examId) {
        examAnswerTrackMapper.deleteByExamId(examId);
    }

    @Override
    public void insertPos(List<ExamAnswerTrack> examAnswerTrackList) {
        examAnswerTrackMapper.insertPos(examAnswerTrackList);
    }
}