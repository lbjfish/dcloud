package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.LightingSteps;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface LightingStepsService extends IBaseService<LightingSteps> {
    List<LightingSteps> selectByLightingSimulatorId(String lightingSimulatorId);
    int selectLightingSimulatorSizeByStringIds(String stringIds);
    void physicalDeleteByLightingSimulatorId(String lightingSimulatorId);
    void deleteByLightingSimulatorId(String lightingSimulatorId);
    void insertPos(List<LightingSteps> lightingStepsList);
}