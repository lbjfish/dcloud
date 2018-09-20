package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dao.ExamNumRuleMapper;
import com.sida.dcloud.training.po.ExamNumRule;
import com.sida.dcloud.training.service.ExamNumRuleService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamNumRuleServiceImpl extends BaseServiceImpl<ExamNumRule> implements ExamNumRuleService {
    private static final Logger LOG = LoggerFactory.getLogger(ExamNumRuleServiceImpl.class);

    @Autowired
    private ExamNumRuleMapper examNumRuleMapper;

    @Override
    public IMybatisDao<ExamNumRule> getBaseDao() {
        return examNumRuleMapper;
    }

    @Override
    public ExamNumRule loadRuleBySubject(String subject) {
        return examNumRuleMapper.loadRuleBySubject(subject);
    }
}