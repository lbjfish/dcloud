package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysDictionary;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysDictionaryService extends IBaseService<SysDictionary> {

    /**
     * 根据id删除字典及下级字典
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 通过父级code获取子级字典
     * @param pCode
     * @return
     */
    List<SysDictionary> selectByPCode(String pCode);

    /**
     * 通过父级id获取子级字典
     * @param pid
     * @return
     */
    List<SysDictionary> selectByPid(String pid);

    /**
     * 加载数据字典到Redis缓存
     */
    void loadDictsToRedis();

}