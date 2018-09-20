/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.dto.GroupCountDto;
import com.sida.dcloud.training.po.Exercise;
import com.sida.dcloud.training.vo.ExerciseVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExerciseMapper extends IMybatisDao<Exercise> {
    int physicalDeleteGroupRelByExercise(@Param("exerciseIds")String exerciseIds);
    int selectExerciseSizeByGroupIds(@Param("groupIds")String groupIds);
    int selectExerciseSizeBySectionIds(@Param("sectionIds")String sectionIds);
    List<ExerciseVo> findVoList(@Param("po")Exercise po);
    int checkMultiCountByUnique(@Param("po")Exercise po);
    List<GroupCountDto> findRemoveCountGroup(@Param("stringIds")String stringIds);
    List<GroupCountDto> findRemoveCountSection(@Param("stringIds")String stringIds);
}