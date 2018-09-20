package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.client.TrainingProxy;
import com.sida.dcloud.training.dao.ChapterSectionMapper;
import com.sida.dcloud.training.po.ChapterSection;
import com.sida.dcloud.training.service.ChapterSectionService;
import com.sida.dcloud.training.vo.ChapterSectionVo;
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
public class ChapterSectionServiceImpl extends BaseServiceImpl<ChapterSection> implements ChapterSectionService {
    private static final Logger logger = LoggerFactory.getLogger(ChapterSectionServiceImpl.class);

    @Autowired
    private ChapterSectionMapper chapterSectionMapper;
    @Autowired
    private TrainingProxy trainingProxy;

    @Override
    public IMybatisDao<ChapterSection> getBaseDao() {
        return chapterSectionMapper;
    }

    @Override
    public Page<ChapterSectionVo> findPageList(ChapterSection po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<ChapterSectionVo> voList = chapterSectionMapper.findVoList(po);
        trainingProxy.fillUserNamesByIds(voList);
        return (Page) voList;
    }

    @Override
    public int checkMultiCountByUnique(ChapterSection po) {
        return chapterSectionMapper.checkMultiCountByUnique(po);
    }

    @Override
    public void increaseTotal(ChapterSection po) {
        chapterSectionMapper.increaseTotal(po);
    }
}