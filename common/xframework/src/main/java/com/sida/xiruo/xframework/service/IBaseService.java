package com.sida.xiruo.xframework.service;

import com.sida.xiruo.po.common.BaseEntity;

import java.util.List;

/**
 * Created by Xiruo on 2017/7/20.
 */
public interface IBaseService<T> {


    //	@RedisEvict(type = Object.class)
    int deleteByPrimaryKey(Object id);

    int deleteByPrimaryKeys(String ids);

    //	@RedisEvict(type = Object.class)
    int insert(T po);

    //	@RedisEvict(type = Object.class)
    int insertSelective(T po);

    //    @RedisCache(type = Object.class, result = RESULT_TYPE_SINGLE)
    T selectByPrimaryKey(Object id);

    //    @RedisEvict(type = Object.class)
    int updateByPrimaryKeySelective(T po);

    //    @RedisEvict(type = Object.class)
    int updateByPrimaryKey(T po);

    /** 全量查询 */
    List<T> selectByCondition();
    /** 条件查询 */
    List<T> selectByCondition(T condition);

    int updateByIdsSelective(T po, List ids);

    int saveOrUpdate(T po);

}
