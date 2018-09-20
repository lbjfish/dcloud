package com.sida.dcloud.training.service;

import com.sida.dcloud.training.dto.ExamRuleMixDto;

public interface ExamRuleMixService {
    ExamRuleMixDto loadRuleBySubject(String subject);
}