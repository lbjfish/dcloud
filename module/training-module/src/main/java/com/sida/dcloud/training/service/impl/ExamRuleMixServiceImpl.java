package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dto.ExamRuleMixDto;
import com.sida.dcloud.training.service.ExamNumRuleService;
import com.sida.dcloud.training.service.ExamRuleMixService;
import com.sida.dcloud.training.service.ExamSectionRuleService;
import com.sida.dcloud.training.service.ExamTypeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamRuleMixServiceImpl implements ExamRuleMixService {
    private static final Logger LOG = LoggerFactory.getLogger(ExamRuleMixServiceImpl.class);
    @Autowired
    private ExamTypeRuleService examTypeRuleService;
    @Autowired
    private ExamSectionRuleService examSectionRuleService;
    @Autowired
    private ExamNumRuleService examNumRuleService;

    @Override
    public ExamRuleMixDto loadRuleBySubject(String subject) {
        ExamRuleMixDto dto = new ExamRuleMixDto();
        //总题量、合格分数、考试时间（分钟）
        dto.setExamNumRule(examNumRuleService.loadRuleBySubject(subject));
        //题型规则
        dto.setExamTypeRuleList(examTypeRuleService.loadRuleBySubject(subject));
        //章节规则
        dto.setExamSectionRuleList(examSectionRuleService.loadRuleBySubject(subject));
        return dto;
    }
}