package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.ExamTypeRule;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ExamTypeRuleService extends IBaseService<ExamTypeRule> {
    List<ExamTypeRule> loadRuleBySubject(String subject);
    void deleteBySubject(String subject);
    void insertPos(List<ExamTypeRule> examTypeRuleList);
}