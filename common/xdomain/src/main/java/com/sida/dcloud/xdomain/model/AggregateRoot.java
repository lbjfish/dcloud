package com.sida.dcloud.xdomain.model;

import com.sida.xiruo.po.common.BaseEntity;

public abstract class AggregateRoot<T extends BaseEntity> extends DomainEntity<T> implements IAggregateRoot {
    public AggregateRoot(T po) {
        super(po);
    }
}
