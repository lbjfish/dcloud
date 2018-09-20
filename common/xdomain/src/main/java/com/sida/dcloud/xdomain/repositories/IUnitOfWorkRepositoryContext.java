package com.sida.dcloud.xdomain.repositories;

public interface IUnitOfWorkRepositoryContext<T> extends IUnitOfWork<T> {
    void registerNew(T entity);
    void registerDirty(T entity);
    void registerRemoved(T entity);
}
