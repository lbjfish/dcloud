package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dao.LightingStepsMapper;
import com.sida.dcloud.training.po.LightingSteps;
import com.sida.dcloud.training.service.LightingStepsService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LightingStepsServiceImpl extends BaseServiceImpl<LightingSteps> implements LightingStepsService {
    private static final Logger logger = LoggerFactory.getLogger(LightingStepsServiceImpl.class);

    @Autowired
    private LightingStepsMapper lightingStepsMapper;

    @Override
    public IMybatisDao<LightingSteps> getBaseDao() {
        return lightingStepsMapper;
    }

    @Override
    public List<LightingSteps> selectByLightingSimulatorId(String lightingSimulatorId) {
        return lightingStepsMapper.selectByLightingSimulatorId(lightingSimulatorId);
    }

    @Override
    public int selectLightingSimulatorSizeByStringIds(String stringIds) {
        return lightingStepsMapper.selectLightingSimulatorSizeByStringIds(stringIds.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", "'"));
    }

    @Override
    public void physicalDeleteByLightingSimulatorId(String lightingSimulatorId) {
        lightingStepsMapper.physicalDeleteByLightingSimulatorId(lightingSimulatorId);
    }

    @Override
    public void deleteByLightingSimulatorId(String lightingSimulatorId) {
        lightingStepsMapper.deleteByLightingSimulatorId(lightingSimulatorId);
    }

    @Override
    public void insertPos(List<LightingSteps> lightingStepsList) {
        lightingStepsMapper.insertPos(lightingStepsList);
    }
}