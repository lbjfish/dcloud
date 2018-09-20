package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.ExamSectionRule;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ExamSectionRuleService extends IBaseService<ExamSectionRule> {
    List<ExamSectionRule> loadRuleBySubject(String subject);
    void deleteBySubject(String subject);
    void insertPos(List<ExamSectionRule> examSectionRuleList);
}