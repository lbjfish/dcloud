package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysDictionaryGroup;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SysDictionaryGroupService extends IBaseService<SysDictionaryGroup> {
    void delete(List<String> ids);

    void update(SysDictionaryGroup param);

    void addGroup(SysDictionaryGroup param);

    Page<SysDictionaryGroup> findPageList(SysDictionaryGroup param);

    void loadDictGroupsToRedis();

    /**
     * 查询字典组，Key为groupCode， value为groupName
     * @return
     */
    Map<String, String> findAllForCache();

}