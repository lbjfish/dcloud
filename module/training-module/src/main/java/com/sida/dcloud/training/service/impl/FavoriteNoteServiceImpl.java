package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.client.TrainingProxy;
import com.sida.dcloud.training.util.TrainingCacheUtil;
import com.sida.dcloud.training.dao.FavoriteNoteMapper;
import com.sida.dcloud.training.po.Exercise;
import com.sida.dcloud.training.po.FavoriteNote;
import com.sida.dcloud.training.service.FavoriteNoteService;
import com.sida.dcloud.training.vo.FavoriteNoteVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteNoteServiceImpl extends BaseServiceImpl<FavoriteNote> implements FavoriteNoteService {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteNoteServiceImpl.class);

    @Autowired
    private FavoriteNoteMapper favoriteNoteMapper;
    @Autowired
    private TrainingProxy trainingProxy;
    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    @Override
    public IMybatisDao<FavoriteNote> getBaseDao() {
        return favoriteNoteMapper;
    }

    @Override
    public Page<FavoriteNoteVo> findPageList(FavoriteNote po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<FavoriteNoteVo> voList = favoriteNoteMapper.findVoList(po);
//        trainingProxy.fillUserNamesByIds(voList);
        Exercise empty = new Exercise();
        voList.forEach(vo -> vo.setQuestion(
                Optional.ofNullable(trainingCacheUtil.getExerciseById(vo.getExerciseId())).orElse(empty).getQuestion()
        ));
        return (Page) voList;
    }
}