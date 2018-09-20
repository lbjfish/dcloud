package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.client.TrainingProxy;
import com.sida.dcloud.training.dao.AudioSimulatorMapper;
import com.sida.dcloud.training.po.AudioSimulator;
import com.sida.dcloud.training.service.AudioSimulatorService;
import com.sida.dcloud.training.vo.AudioSimulatorVo;
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
public class AudioSimulatorServiceImpl extends BaseServiceImpl<AudioSimulator> implements AudioSimulatorService {
    private static final Logger logger = LoggerFactory.getLogger(AudioSimulatorServiceImpl.class);

    @Autowired
    private AudioSimulatorMapper audioSimulatorMapper;
    @Autowired
    private TrainingProxy trainingProxy;

    @Override
    public IMybatisDao<AudioSimulator> getBaseDao() {
        return audioSimulatorMapper;
    }

    @Override
    public Page<AudioSimulatorVo> findPageList(AudioSimulator po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<AudioSimulatorVo> voList = audioSimulatorMapper.findVoList(po);
        trainingProxy.fillUserNamesByIds(voList);
        return (Page) voList;

    }

    @Override
    public int checkMultiCountByUnique(AudioSimulator po) {
        return audioSimulatorMapper.checkMultiCountByUnique(po);
    }
}