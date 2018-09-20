package com.sida.dcloud.training.uow;

import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.model.ExamRuleMixModel;
import com.sida.dcloud.training.po.ExamNumRule;
import com.sida.dcloud.training.po.ExamSectionRule;
import com.sida.dcloud.training.po.ExamTypeRule;
import com.sida.dcloud.training.service.ExamNumRuleService;
import com.sida.dcloud.training.service.ExamSectionRuleService;
import com.sida.dcloud.training.service.ExamTypeRuleService;
import com.sida.dcloud.xdomain.common.SpringBeansManager;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class ExamRuleMixUnitOfWork extends MybatisUnitOfWork<ExamRuleMixModel> {
    private static final Logger LOG = LoggerFactory.getLogger(ExamRuleMixUnitOfWork.class);

    private ExamTypeRuleService examTypeRuleService;
    private ExamSectionRuleService examSectionRuleService;
    private ExamNumRuleService examNumRuleService;

    public ExamRuleMixUnitOfWork() {
        examNumRuleService = (ExamNumRuleService) SpringBeansManager.getInstance().getServiceBean(ExamNumRule.class.getSimpleName());
        examTypeRuleService = (ExamTypeRuleService) SpringBeansManager.getInstance().getServiceBean(ExamTypeRule.class.getSimpleName());
        examSectionRuleService = (ExamSectionRuleService) SpringBeansManager.getInstance().getServiceBean(ExamSectionRule.class.getSimpleName());
    }

    @Override
    public void insertNew() {
        throw new TrainingException("Unsupport the method.");
    }

    @Override
    public void deleteRemoved() {
        throw new TrainingException("Unsupport the method.");
    }

    @Override
    @Transactional
    public void updateDirty() {
        Optional.ofNullable(examTypeRuleService).orElseThrow(() -> new TrainingException("未做必要的初始化"));
        Optional.ofNullable(examSectionRuleService).orElseThrow(() -> new TrainingException("未做必要的初始化"));
        Optional.ofNullable(examNumRuleService).orElseThrow(() -> new TrainingException("未做必要的初始化"));
        dirtyEntities.forEach(entity -> {
            examNumRuleService.saveOrUpdate(entity.getPo().getExamNumRule());
            examTypeRuleService.deleteBySubject(entity.getPo().getSubject());
            examTypeRuleService.insertPos(entity.getPo().getExamTypeRuleList());
            examSectionRuleService.deleteBySubject(entity.getPo().getSubject());
            examSectionRuleService.insertPos(entity.getPo().getExamSectionRuleList());
        });
    }
}
