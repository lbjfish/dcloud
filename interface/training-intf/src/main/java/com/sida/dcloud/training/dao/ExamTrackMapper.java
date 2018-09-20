/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.ExamTrack;
import com.sida.dcloud.training.vo.ExamTrackVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamTrackMapper extends IMybatisDao<ExamTrack> {
    List<ExamTrackVo> findVoList(@Param("po")ExamTrack po);
    void completeExam(@Param("po")ExamTrack po);
    void shuffleExam(@Param("po")ExamTrack po);
}