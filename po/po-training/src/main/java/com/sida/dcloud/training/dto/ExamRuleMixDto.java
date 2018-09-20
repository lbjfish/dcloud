package com.sida.dcloud.training.dto;

import com.sida.dcloud.training.po.ExamNumRule;
import com.sida.dcloud.training.po.ExamSectionRule;
import com.sida.dcloud.training.po.ExamTypeRule;
import com.sida.xiruo.po.common.BaseEntity;

import java.util.List;

public class ExamRuleMixDto extends BaseEntity {
    private String subject;
    private ExamNumRule examNumRule;
    private List<ExamTypeRule> examTypeRuleList;
    private List<ExamSectionRule> examSectionRuleList;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ExamNumRule getExamNumRule() {
        return examNumRule;
    }

    public void setExamNumRule(ExamNumRule examNumRule) {
        this.examNumRule = examNumRule;
    }

    public List<ExamTypeRule> getExamTypeRuleList() {
        return examTypeRuleList;
    }

    public void setExamTypeRuleList(List<ExamTypeRule> examTypeRuleList) {
        this.examTypeRuleList = examTypeRuleList;
    }

    public List<ExamSectionRule> getExamSectionRuleList() {
        return examSectionRuleList;
    }

    public void setExamSectionRuleList(List<ExamSectionRule> examSectionRuleList) {
        this.examSectionRuleList = examSectionRuleList;
    }
}
