package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.LightingSimulator;
import com.sida.dcloud.training.vo.LightingSimulatorVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

public interface LightingSimulatorService extends IBaseService<LightingSimulator> {
    Page<LightingSimulatorVo> findPageList(LightingSimulator po);
    int checkMultiCountByUnique(LightingSimulator po);
}