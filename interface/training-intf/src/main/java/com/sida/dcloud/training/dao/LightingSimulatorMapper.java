/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.LightingSimulator;
import com.sida.dcloud.training.vo.LightingSimulatorVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LightingSimulatorMapper extends IMybatisDao<LightingSimulator> {
    List<LightingSimulatorVo> findVoList(@Param("po")LightingSimulator po);
    int checkMultiCountByUnique(@Param("po")LightingSimulator po);
}