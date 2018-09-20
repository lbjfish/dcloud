package com.sida.dcloud.xdomain.dispatcher;

public interface IEventDispatcher<T> {
    void insert(T entity);
    void update(T entity);
//    void remove(T entity);
    void remove(String stringIds);
}
