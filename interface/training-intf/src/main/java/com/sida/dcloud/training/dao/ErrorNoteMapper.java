/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.ErrorNote;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ErrorNoteMapper extends IMybatisDao<ErrorNote> {
    List<ErrorNote> selectByUserWithCorrected(@Param("po")ErrorNote po);
    void physicalDeleteByPrimaryKey(@Param("id")String id);
    void physicalDeleteByPrimaryKeys(@Param("ids")String stringIds);
    void deleteByExerciseIdAndUserId(@Param("po")ErrorNote po);
    void physicalDeleteByExerciseIdAndUserId(@Param("po")ErrorNote po);
    void updateMaybeCorrect(@Param("po")ErrorNote po);
}