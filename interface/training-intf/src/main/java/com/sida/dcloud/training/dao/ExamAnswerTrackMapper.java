/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.ExamAnswerTrack;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamAnswerTrackMapper extends IMybatisDao<ExamAnswerTrack> {
    List<ExamAnswerTrack> selectByExamId(@Param("examId")String examId);
    void physicalDeleteByExamId(@Param("examId")String examId);
    void deleteByExamId(@Param("examId")String examId);
    void insertPos(@Param("examAnswerTrackList")List<ExamAnswerTrack> examAnswerTrackList);
}