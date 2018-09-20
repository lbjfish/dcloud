package com.sida.dcloud.event.po;

import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.po.common.BaseEntity;

import java.io.Serializable;
import java.util.UUID;

//@Document(collection = "eslogs")
public class Event<T extends BaseEntity, E extends EventType> implements Serializable {

    public static <T extends  BaseEntity, E extends EventType> Event makeEvent(T entity, E eventType) {
        return makeEvent(entity, eventType, "0", 0);
    }

    public static <T extends  BaseEntity, E extends EventType> Event makeEvent(T entity, E eventType, String transactionId, int transactionSize) {
        Event event = new Event();
        event.eventId = UUID.randomUUID().toString();
        event.entityType = entity.getClass().getName();
        event.eventType = eventType;
        event.eventTime = System.currentTimeMillis();
        event.entityId = entity.getId();
        event.eventEntity = entity;
        event.transactionId = transactionId;
        event.transactionSize = transactionSize;
        return event;
    }

    protected Event() {}

//    @Id
    private String transactionId;
    private int transactionSize;

    public int getTransactionSize() {
        return transactionSize;
    }

    public void setTransactionSize(int transactionSize) {
        this.transactionSize = transactionSize;
    }

    private String eventId;
    private E eventType;
    private String entityType;
    private String entityId;
    private long eventTime;
    private T eventEntity;

    @Override
    public String toString() {
        return String.format("This is info for Event instance:\r\ntransactinId[%s], eventId[%s], eventType[%s], entityType[%s], entityId[%s], eventTime[%s], eventEntity[%s]",
                transactionId, eventId, eventType, entityType, entityId, Xiruo.dateToString(eventTime), eventEntity);
    }

    public T getEventEntity() {
        return eventEntity;
    }

    public void setEventEntity(T eventEntity) {
        this.eventEntity = eventEntity;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public E getEventType() {
        return eventType;
    }

    public void setEventType(E eventType) {
        this.eventType = eventType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}