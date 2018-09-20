package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dao.ExamTypeRuleMapper;
import com.sida.dcloud.training.po.ExamTypeRule;
import com.sida.dcloud.training.service.ExamTypeRuleService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamTypeRuleServiceImpl extends BaseServiceImpl<ExamTypeRule> implements ExamTypeRuleService {
    private static final Logger logger = LoggerFactory.getLogger(ExamTypeRuleServiceImpl.class);

    @Autowired
    private ExamTypeRuleMapper examTypeRuleMapper;

    @Override
    public IMybatisDao<ExamTypeRule> getBaseDao() {
        return examTypeRuleMapper;
    }

    @Override
    public List<ExamTypeRule> loadRuleBySubject(String subject) {
        return examTypeRuleMapper.loadRuleBySubject(subject);
    }

    @Override
    public void deleteBySubject(String subject) {
        examTypeRuleMapper.deleteBySubject(subject);
    }

    @Override
    public void insertPos(List<ExamTypeRule> examTypeRuleList) {
        examTypeRuleMapper.insertPos(examTypeRuleList);
    }
}