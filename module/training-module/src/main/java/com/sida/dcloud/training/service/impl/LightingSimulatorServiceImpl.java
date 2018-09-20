package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.client.TrainingProxy;
import com.sida.dcloud.training.dao.LightingSimulatorMapper;
import com.sida.dcloud.training.po.LightingSimulator;
import com.sida.dcloud.training.service.LightingSimulatorService;
import com.sida.dcloud.training.service.LightingStepsService;
import com.sida.dcloud.training.vo.LightingSimulatorVo;
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
public class LightingSimulatorServiceImpl extends BaseServiceImpl<LightingSimulator> implements LightingSimulatorService {
    private static final Logger logger = LoggerFactory.getLogger(LightingSimulatorServiceImpl.class);

    @Autowired
    private LightingSimulatorMapper lightingSimulatorMapper;
    @Autowired
    private LightingStepsService lightingStepsService;
    @Autowired
    private TrainingProxy trainingProxy;

    @Override
    public IMybatisDao<LightingSimulator> getBaseDao() {
        return lightingSimulatorMapper;
    }

    @Override
    public Page<LightingSimulatorVo> findPageList(LightingSimulator po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<LightingSimulatorVo> voList = lightingSimulatorMapper.findVoList(po);
        trainingProxy.fillUserNamesByIds(voList);
        return (Page) voList;
    }

    @Override
    public int checkMultiCountByUnique(LightingSimulator po) {
        return lightingSimulatorMapper.checkMultiCountByUnique(po);
    }
}