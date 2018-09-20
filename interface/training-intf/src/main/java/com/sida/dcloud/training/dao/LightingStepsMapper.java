/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.LightingSteps;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LightingStepsMapper extends IMybatisDao<LightingSteps> {
    List<LightingSteps> selectByLightingSimulatorId(@Param("lightingSimulatorId")String lightingSimulatorId);
    int selectLightingSimulatorSizeByStringIds(@Param("stringIds")String stringIds);
    void physicalDeleteByLightingSimulatorId(@Param("lightingSimulatorId")String lightingSimulatorId);
    void deleteByLightingSimulatorId(@Param("lightingSimulatorId")String lightingSimulatorId);
    void insertPos(@Param("lightingStepsList")List<LightingSteps> lightingStepsList);
}