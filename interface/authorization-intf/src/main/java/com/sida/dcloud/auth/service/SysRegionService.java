package com.sida.dcloud.auth.service;

import com.sida.dcloud.auth.po.SysRegion;
import com.sida.dcloud.auth.vo.RegionTreeDTO;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysRegionService extends IBaseService<SysRegion> {

    /**
     * 获取地区树
     * @return
     */
    List<RegionTreeDTO> findTree();

    /**
     * 根据路径删除区域信息
     * @param id
     */
    int deleteById(String id);
}