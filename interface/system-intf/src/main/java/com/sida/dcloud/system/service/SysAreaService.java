package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.system.po.SysArea;
import com.sida.dcloud.system.vo.SysAreaVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SysAreaService extends IBaseService<SysArea> {

    /**
     * 条件查询片区列表
     * @param area
     * @return
     */
    Page<SysAreaVo> findPageList(SysArea area);

    /**
     * 根据ids获取片区Map
     * @param ids
     * @return
     */
    Map<String,SysArea> findMapByIdIn(List<String> ids);

    /**
     * 更新片区
     * @param orgList
     */
    void updateWithOrgs(List<SysOrg> orgList);

    /**
     * 逻辑删除片区
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 新增片区
     * @param orgList
     */
    void insertWithOrgs(List<SysOrg> orgList);

    /**
     * 物理删除所有
     */
    void removeAll();

    /**
     * 根据组织全量录入片区信息
     */
    void insertWithAllOrgs();
}