package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysDictionaryData;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SysDictionaryDataService extends IBaseService<SysDictionaryData> {
    Page<SysDictionaryData> findPageList(SysDictionaryData param);

    void addData(SysDictionaryData param);

    void update(SysDictionaryData param);

    void delete(List<String> ids);

    /**
     * 查询所有字典用于缓存
     * @return
     */
    Map<String, Map<String, String>> findAllForCache();


    /**
     * 加载数据字典到Redis缓存
     */
    void loadDictsToRedis();
}