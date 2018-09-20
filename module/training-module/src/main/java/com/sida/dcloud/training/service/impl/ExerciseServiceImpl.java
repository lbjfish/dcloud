package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.client.TrainingProxy;
import com.sida.dcloud.training.dao.ExerciseMapper;
import com.sida.dcloud.training.dto.GroupCountDto;
import com.sida.dcloud.training.po.Exercise;
import com.sida.dcloud.training.service.ExerciseService;
import com.sida.dcloud.training.vo.ExerciseVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl extends BaseServiceImpl<Exercise> implements ExerciseService {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    @Autowired
    private ExerciseMapper exerciseMapper;
    @Autowired
    private TrainingProxy trainingProxy;

    @Override
    public IMybatisDao<Exercise> getBaseDao() {
        return exerciseMapper;
    }

    @Override
    public int physicalDeleteGroupRelByExercise(String exerciseIds) {
        return exerciseMapper.physicalDeleteGroupRelByExercise(exerciseIds.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", "'"));
    }

    @Override
    public int selectExerciseSizeByGroupIds(String groupIds) {
        return exerciseMapper.selectExerciseSizeByGroupIds(groupIds.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", "'"));
    }

    @Override
    public int selectExerciseSizeBySectionIds(String sectionIds) {
        return exerciseMapper.selectExerciseSizeBySectionIds(sectionIds.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", "'"));
    }

    @Override
    public List<ExerciseVo> findPageList(Exercise po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<ExerciseVo> voList = exerciseMapper.findVoList(po);
        trainingProxy.fillUserNamesByIds(voList);
        return (Page) voList;
    }

    @Override
    public int checkMultiCountByUnique(Exercise po) {
        return exerciseMapper.checkMultiCountByUnique(po);
    }

    @Override
    public List<GroupCountDto> findRemoveCountGroup(String stringIds) {
        return exerciseMapper.findRemoveCountGroup(stringIds.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", "'"));
    }

    @Override
    public List<GroupCountDto> findRemoveCountSection(String stringIds) {
        return exerciseMapper.findRemoveCountSection(stringIds.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", "'"));
    }
}