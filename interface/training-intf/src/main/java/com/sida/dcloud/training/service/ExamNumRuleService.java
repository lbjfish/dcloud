package com.sida.dcloud.training.service;

import com.sida.dcloud.training.po.ExamNumRule;
import com.sida.xiruo.xframework.service.IBaseService;

public interface ExamNumRuleService extends IBaseService<ExamNumRule> {
    ExamNumRule loadRuleBySubject(String subject);
}