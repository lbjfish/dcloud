package com.sida.dcloud.training.uow;

import com.sida.dcloud.training.model.LightingSimulatorModel;
import com.sida.dcloud.training.po.LightingSimulator;
import com.sida.dcloud.training.po.LightingSteps;
import com.sida.dcloud.training.service.LightingStepsService;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;
import com.sida.xiruo.xframework.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class LightingSimulatorUnitOfWork extends MybatisUnitOfWork<LightingSimulatorModel> {
    private static final Logger LOG = LoggerFactory.getLogger(LightingSimulatorUnitOfWork.class);

    private LightingStepsService lightingStepsService;

    public void setLightingStepsService(LightingStepsService lightingStepsService) {
        this.lightingStepsService = lightingStepsService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertNew() {
        newEntities.forEach(entity -> {
            service.insert(entity.getPo());
            insertSteps(entity.getPo());
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDirty() {
        dirtyEntities.forEach(entity -> {
            service.updateByPrimaryKey(entity.getPo());
            String id = entity.getPo().getId();
            lightingStepsService.physicalDeleteByLightingSimulatorId(id);
            lightingStepsService.deleteByLightingSimulatorId(id);
            insertSteps(entity.getPo());
        });
    }

    private void insertSteps(LightingSimulator po) {
        List<LightingSteps> list = po.getLightingStepsList();
        if(!list.isEmpty()) {
            list.forEach(step -> {
                step.setId(UUIDGenerate.getNextId());
                step.setLightingSimulatorId(po.getId());
            });
            lightingStepsService.insertPos(list);
        }
    }
}
