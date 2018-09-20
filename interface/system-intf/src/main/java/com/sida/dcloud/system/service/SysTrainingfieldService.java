package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysTrainingfield;
import com.sida.dcloud.system.vo.SysTrainingfieldVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

public interface SysTrainingfieldService extends IBaseService<SysTrainingfield> {
    /**
     * 获取分页列表
     * @param param
     * @return
     */
    Page<SysTrainingfieldVo> findPageList(SysTrainingfieldVo param);

    /**
     * 保存训练场
     * @param param
     */
    void save(SysTrainingfieldVo param);

    /**
     * 获取vo
     * @param id
     * @return
     */
    SysTrainingfieldVo selectVoByPrimaryKey(String id);
}