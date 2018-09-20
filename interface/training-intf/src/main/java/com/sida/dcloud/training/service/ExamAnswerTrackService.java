package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.ExamAnswerTrack;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ExamAnswerTrackService extends IBaseService<ExamAnswerTrack> {
    List<ExamAnswerTrack> selectByExamId(String examId);
    void physicalDeleteByExamId(String examkId);
    void deleteByExamId(String examId);
    void insertPos(List<ExamAnswerTrack> examAnswerTrackList);
}