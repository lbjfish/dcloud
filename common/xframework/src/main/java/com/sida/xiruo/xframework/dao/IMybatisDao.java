package com.sida.xiruo.xframework.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Xiruo on 2017/7/20.
 */
public interface IMybatisDao<T> {

    int deleteByPrimaryKey(@Param("id") Object id);

    int deleteByPrimaryKeys(@Param("ids") String ids);

    int insert(T po);

    int insertSelective(T po);

    T selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(T po);

    int updateByPrimaryKey(T po);

    String selectId( String name);

    List<T> selectByCondition(T po);

    int updateByIdsSelective(@Param("po")T po, @Param("ids")List ids);

}
