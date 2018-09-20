/**
 * create by yjr
 * @date 2017-08
 */
package com.sida.dcloud.system.dao;

import com.sida.dcloud.system.po.SysDictionary;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictionaryMapper extends IMybatisDao<SysDictionary> {

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
    List<SysDictionary> selectByPCode(@Param(value = "pCode") String pCode);


    List<SysDictionary> selectByPid(@Param(value = "pid") String pid);


    /**
     * 查询所有数据字典
     * @return
     */
    List<SysDictionary> findAllDicts();

}