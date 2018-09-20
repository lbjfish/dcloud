package com.sida.xiruo.xframework.service;


import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.po.common.BaseEntity;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Xiruo on 2017/7/20.
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements IBaseService<T> {

    public abstract IMybatisDao<T> getBaseDao();

    @Override
    public int deleteByPrimaryKey(Object id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
        return getBaseDao().deleteByPrimaryKeys(ids.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", "'"));
    }

    @Override
    public int insert(T po) {
        return getBaseDao().insert(po);
    }

    @Override
    public int insertSelective(T po) {
        return getBaseDao().insertSelective(po);
    }

    @Override
    public T selectByPrimaryKey(Object id) {
        return getBaseDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T po) {
        return getBaseDao().updateByPrimaryKeySelective(po);
    }

    @Override
    public int updateByPrimaryKey(T po) {
        return getBaseDao().updateByPrimaryKey(po);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> selectByCondition() {
        T po = createInstance();
        po.setDeleteFlag(false);
        po.setDisable(false);
        return selectByCondition(po);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> selectByCondition(T condition) {
        return getBaseDao().selectByCondition(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateByIdsSelective(T po, List ids) {
        return getBaseDao().updateByIdsSelective(po, ids);
    }

    @Override
    public int saveOrUpdate(T po){
        try {
            Method getId = po.getClass().getMethod("getId");
            String id = getId.invoke(po)==null?null:getId.invoke(po).toString();
            if (StringUtils.isBlank(id)){
                PoDefaultValueGenerator.putDefaultValue(po);
                return getBaseDao().insertSelective(po);
            }else {
                PoDefaultValueGenerator.putDefaultValue(po);
                return getBaseDao().updateByPrimaryKeySelective(po);
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    public T createInstance() {
        try {
            Type superClass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            Class<?> clazz = Xiruo.getRawType(type);
            return (T) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
