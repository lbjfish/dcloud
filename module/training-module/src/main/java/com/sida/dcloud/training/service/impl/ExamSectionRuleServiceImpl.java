package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dao.ExamSectionRuleMapper;
import com.sida.dcloud.training.po.ExamSectionRule;
import com.sida.dcloud.training.service.ExamSectionRuleService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamSectionRuleServiceImpl extends BaseServiceImpl<ExamSectionRule> implements ExamSectionRuleService {
    private static final Logger logger = LoggerFactory.getLogger(ExamSectionRuleServiceImpl.class);

    @Autowired
    private ExamSectionRuleMapper examSectionRuleMapper;

    @Override
    public IMybatisDao<ExamSectionRule> getBaseDao() {
        return examSectionRuleMapper;
    }

    @Override
    public List<ExamSectionRule> loadRuleBySubject(String subject) {
        return examSectionRuleMapper.loadRuleBySubject(subject);
    }

    @Override
    public void deleteBySubject(String subject) {
        examSectionRuleMapper.deleteBySubject(subject);
    }

    @Override
    public void insertPos(List<ExamSectionRule> examSectionRuleList) {
        examSectionRuleMapper.insertPos(examSectionRuleList);
    }
}