package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.AudioSimulator;
import com.sida.dcloud.training.vo.AudioSimulatorVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

public interface AudioSimulatorService extends IBaseService<AudioSimulator> {
    Page<AudioSimulatorVo> findPageList(AudioSimulator po);
    int checkMultiCountByUnique(AudioSimulator po);
}