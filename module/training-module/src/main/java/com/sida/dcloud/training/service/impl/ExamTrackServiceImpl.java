package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dao.ExamTrackMapper;
import com.sida.dcloud.training.po.ExamTrack;
import com.sida.dcloud.training.service.ExamTrackService;
import com.sida.dcloud.training.vo.ExamTrackVo;
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
public class ExamTrackServiceImpl extends BaseServiceImpl<ExamTrack> implements ExamTrackService {
    private static final Logger logger = LoggerFactory.getLogger(ExamTrackServiceImpl.class);

    @Autowired
    private ExamTrackMapper examTrackMapper;

    @Override
    public IMybatisDao<ExamTrack> getBaseDao() {
        return examTrackMapper;
    }

    @Override
    public List<ExamTrackVo> findPageList(ExamTrack po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<ExamTrackVo> voList = examTrackMapper.findVoList(po);
        return (Page) voList;
    }

    @Override
    public void completeExam(ExamTrack po) {
        examTrackMapper.completeExam(po);
    }

    @Override
    public void shuffleExam(ExamTrack po) {
        examTrackMapper.shuffleExam(po);
    }
}