package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.ExamTrack;
import com.sida.dcloud.training.vo.ExamTrackVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ExamTrackService extends IBaseService<ExamTrack> {
    List<ExamTrackVo> findPageList(ExamTrack po);
    void completeExam(ExamTrack po);
    void shuffleExam(ExamTrack po);

}