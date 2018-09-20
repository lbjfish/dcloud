package com.sida.dcloud.xdomain.model;

import com.sida.dcloud.xdomain.repositories.IUnitOfWorkRepositoryContext;
import com.sida.xiruo.po.common.BaseEntity;
import com.sida.xiruo.xframework.exception.BaseRuntimeException;

import java.util.Optional;

/**
 * 充血模式
 */
public abstract class DomainEntity<T extends BaseEntity> implements IEntity {
    public static <M extends DomainEntity> void createModel(M model) { model.makeNew(); }
    public static <M extends DomainEntity> void modifyModel(M model) { model.makeDirty(); }
    public static <M extends DomainEntity> void removeModel(M model) { model.makeRemoved(); }

    private T po;

    private IUnitOfWorkRepositoryContext unitOfWork;

    public void bindUnitOfWork(IUnitOfWorkRepositoryContext unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public IUnitOfWorkRepositoryContext getUnitOfWork() {
        return unitOfWork;
    }

    public DomainEntity(T po) {
        this.po = po;
    }

    public T getPo() {
        return po;
    }

    protected void makeNew() {
        checkUnitOfWork();
        unitOfWork.registerNew(this);
    }

    protected void makeDirty() {
        checkUnitOfWork();
        unitOfWork.registerDirty(this);
    }

    protected void makeRemoved() {
        checkUnitOfWork();
        unitOfWork.registerRemoved(this);
    }

    protected void checkUnitOfWork() {
        Optional.ofNullable(unitOfWork).orElseThrow(() -> new BaseRuntimeException("尚未初始化unitOfWork"));
    }
}
