/**
 * create by Administrator
 * @date 2018-08
 */
package com.sida.dcloud.training.dao;

import com.sida.dcloud.training.po.AudioSimulator;
import com.sida.dcloud.training.vo.AudioSimulatorVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AudioSimulatorMapper extends IMybatisDao<AudioSimulator> {
    List<AudioSimulatorVo> findVoList(@Param("po")AudioSimulator po);
    int checkMultiCountByUnique(@Param("po")AudioSimulator po);
}