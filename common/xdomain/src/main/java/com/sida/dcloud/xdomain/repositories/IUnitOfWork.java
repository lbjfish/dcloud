package com.sida.dcloud.xdomain.repositories;

public interface IUnitOfWork<T> {
    void clear();
    void checkAll();
    void checkEntity(T entity);
    void commit();
    void insertNew();
    void updateDirty();
    void deleteRemoved();
}