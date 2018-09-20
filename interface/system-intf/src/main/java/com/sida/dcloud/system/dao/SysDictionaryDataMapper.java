/**
 * create by Administrator
 * @date 2017-10
 */
package com.sida.dcloud.system.dao;

import com.sida.dcloud.system.po.SysDictionaryData;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictionaryDataMapper extends IMybatisDao<SysDictionaryData> {

    List<SysDictionaryData> findAllForCache();

    List<SysDictionaryData> selectByPrimaryKeys(@Param("ids") List<String> ids);
}