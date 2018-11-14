package com.sida.dcloud.content.service.impl;

import com.sida.dcloud.content.dao.SpecialSubjectMapper;
import com.sida.dcloud.content.po.SpecialSubject;
import com.sida.dcloud.content.service.SpecialSubjectService;
import com.sida.dcloud.content.vo.SpecialSubjectVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialSubjectServiceImpl extends BaseServiceImpl<SpecialSubject> implements SpecialSubjectService {
    private static final Logger logger = LoggerFactory.getLogger(SpecialSubjectServiceImpl.class);

    @Autowired
    private SpecialSubjectMapper specialSubjectMapper;

    @Override
    public IMybatisDao<SpecialSubject> getBaseDao() {
        return specialSubjectMapper;
    }


    @Override
    public SpecialSubjectVo findSpecialToChildsByType(String subjectCategory) {
        return specialSubjectMapper.findSpecialToChildsByType(subjectCategory);
    }
}