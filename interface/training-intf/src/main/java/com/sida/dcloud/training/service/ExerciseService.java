package com.sida.dcloud.training.service;

import com.sida.dcloud.training.dto.GroupCountDto;
import com.sida.dcloud.training.po.Exercise;
import com.sida.dcloud.training.vo.ExerciseVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ExerciseService extends IBaseService<Exercise> {
    int physicalDeleteGroupRelByExercise(String exerciseIds);
    int selectExerciseSizeByGroupIds(String groupIds);
    int selectExerciseSizeBySectionIds(String sectionIds);
    List<ExerciseVo> findPageList(Exercise po);
    int checkMultiCountByUnique(Exercise po);
    List<GroupCountDto> findRemoveCountGroup(String stringIds);
    List<GroupCountDto> findRemoveCountSection(String stringIds);
}