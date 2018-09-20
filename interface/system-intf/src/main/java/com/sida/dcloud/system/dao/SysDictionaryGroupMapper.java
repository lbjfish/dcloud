/**
 * create by Administrator
 * @date 2017-10
 */
package com.sida.dcloud.system.dao;

import com.sida.dcloud.system.po.SysDictionaryGroup;
import com.sida.xiruo.xframework.dao.IMybatisDao;

import java.util.List;

public interface SysDictionaryGroupMapper extends IMybatisDao<SysDictionaryGroup> {

    List<SysDictionaryGroup> selectByPrimaryKeys(List<String> ids);

    List<SysDictionaryGroup> findAllForCache();

}