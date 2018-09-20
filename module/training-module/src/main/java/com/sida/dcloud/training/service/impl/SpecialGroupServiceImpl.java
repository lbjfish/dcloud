package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.client.TrainingProxy;
import com.sida.dcloud.training.dao.SpecialGroupMapper;
import com.sida.dcloud.training.po.SpecialGroup;
import com.sida.dcloud.training.service.SpecialGroupService;
import com.sida.dcloud.training.vo.SpecialGroupVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialGroupServiceImpl extends BaseServiceImpl<SpecialGroup> implements SpecialGroupService {
    private static final Logger logger = LoggerFactory.getLogger(SpecialGroupServiceImpl.class);

    @Autowired
    private SpecialGroupMapper specialGroupMapper;
    @Autowired
    private TrainingProxy trainingProxy;
    @Override
    public IMybatisDao<SpecialGroup> getBaseDao() {
        return specialGroupMapper;
    }

    @Override
    public Page<SpecialGroupVo> findPageList(SpecialGroup po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<SpecialGroupVo> voList = specialGroupMapper.findVoList(po);
        trainingProxy.fillUserNamesByIds(voList);
        return (Page) voList;
    }

    @Override
    public int checkMultiCountByUnique(SpecialGroup po) {
        return specialGroupMapper.checkMultiCountByUnique(po);
    }

    @Override
    public void increaseTotal(SpecialGroup po) {
        specialGroupMapper.increaseTotal(po);
    }
}