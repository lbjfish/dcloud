package com.sida.dcloud.xdomain.repositories.impl.mybatis;

import com.sida.dcloud.xdomain.common.SpringBeansManager;
import com.sida.dcloud.xdomain.model.DomainEntity;
import com.sida.dcloud.xdomain.repositories.IUnitOfWorkRepositoryContext;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.exception.BaseRuntimeException;
import com.sida.xiruo.xframework.service.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MybatisUnitOfWork<T extends DomainEntity> implements IUnitOfWorkRepositoryContext<T> {
    private final static Logger LOG = LoggerFactory.getLogger(MybatisUnitOfWork.class);

    protected IBaseService service;

    protected List<T> newEntities = new ArrayList<T>();
    protected List<T> dirtyEntities = new ArrayList<T>();
    protected List<T> removedEntities = new ArrayList<T>();

    public MybatisUnitOfWork() {
        service = (IBaseService)SpringBeansManager.getInstance().getServiceBean(createInstance().getPo().getClass().getSimpleName());
    }

    protected IBaseService getService() {
        return service;
    }

    @Override
    public void clear() {
        newEntities.clear();
        dirtyEntities.clear();
        removedEntities.clear();
    }

    @Override
    public void checkAll() {
        LOG.info("newEntities cout: {}", newEntities.size());
        LOG.info("dirtyEntities cout: {}", dirtyEntities.size());
        LOG.info("removedEntities cout: {}", removedEntities.size());
        newEntities.forEach(entity -> LOG.info("newEntity = {} - [id: {}]", entity.toString(), entity.getPo().getId()));
        dirtyEntities.forEach(entity -> LOG.info("dirtyEntity = {} - [id: {}]", entity.toString(), entity.getPo().getId()));
        removedEntities.forEach(entity -> LOG.info("removedEntity = {} - [id: {}]", entity.toString(), entity.getPo().getId()));
    }

    @Override
    public void checkEntity(T entity) {
        LOG.info("Is there any existence in newEntities, {} - [id: {}]: {}", entity.toString(), entity.getPo().getId(), newEntities.contains(entity));
        LOG.info("Is there any existence in dirtyEntities, {} - [id: {}]: {}", entity.toString(), entity.getPo().getId(), dirtyEntities.contains(entity));
        LOG.info("Is there any existence in removedEntities, {} - [id: {}]: {}", entity.toString(), entity.getPo().getId(), removedEntities.contains(entity));
    }

    @Override
    public void registerNew(T entity) {
        Optional.ofNullable(entity.getPo().getId()).orElseThrow(() -> new BaseRuntimeException("id不能空"));
        if(dirtyEntities.contains(entity)) {
            throw new BaseRuntimeException("新对象不能同时是一个脏对象");
        }
        if(removedEntities.contains(entity)) {
            throw new BaseRuntimeException("新对象不能同时是一个要删除的对象");
        }
        if(newEntities.contains(entity)) {
            throw new BaseRuntimeException("新对象已经注册，不要重复");
        }
        newEntities.add(entity);
        LOG.debug("新对象添加成功, {} - [id: {}]: {}", entity.toString(), entity.getPo().getId());
    }

    @Override
    public void registerDirty(T entity) {
        Optional.ofNullable(entity.getPo().getId()).orElseThrow(() -> new BaseRuntimeException("id不能空"));
        if(removedEntities.contains(entity)) {
            throw new BaseRuntimeException("脏对象不能同时是一个要删除的对象");
        }
        if(!newEntities.contains(entity) && !dirtyEntities.contains(entity)) {
            dirtyEntities.add(entity);
            LOG.debug("脏对象添加成功, {} - [id: {}]: {}", entity.toString(), entity.getPo().getId());
        }
    }

    @Override
    public void registerRemoved(T entity) {
        Optional.ofNullable(entity.getPo().getStringIds()).orElseThrow(() -> new BaseRuntimeException("stringIds不能空"));
            removedEntities.add(entity);
            LOG.debug("删除对象添加成功, {} - [id: {}]: {}", entity.toString(), entity.getPo().getStringIds());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void commit() {
        insertNew();
        updateDirty();
        deleteRemoved();
        clear();
    }

    @Override
    public void insertNew() {
        newEntities.forEach(entity -> service.insert(entity.getPo()));
    }

    @Override
    public void updateDirty() {
        dirtyEntities.forEach(entity -> service.updateByPrimaryKey(entity.getPo()));
    }

    @Override
    public void deleteRemoved()  {
        removedEntities.forEach(entity -> service.deleteByPrimaryKeys(entity.getPo().getStringIds().replaceAll("^|$|(?=,[^,]*)|(?<=[^,]*,)", "'")) );
    }

    public static void main(String[] args) {
        System.out.println("dfasfasd,ewrerewwr,432423".replaceAll("^|$|(?=,[^,]*?)|(?<=[^,]*?,)", "'"));
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
