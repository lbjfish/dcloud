package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.client.TrainingProxy;
import com.sida.dcloud.training.dao.IconGroupMapper;
import com.sida.dcloud.training.po.IconGroup;
import com.sida.dcloud.training.service.IconGroupService;
import com.sida.dcloud.training.vo.IconGroupVo;
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
public class IconGroupServiceImpl extends BaseServiceImpl<IconGroup> implements IconGroupService {
    private static final Logger logger = LoggerFactory.getLogger(IconGroupServiceImpl.class);

    @Autowired
    private IconGroupMapper iconGroupMapper;
    @Autowired
    private TrainingProxy trainingProxy;

    @Override
    public IMybatisDao<IconGroup> getBaseDao() {
        return iconGroupMapper;
    }


    @Override
    public Page<IconGroupVo> findPageList(IconGroup po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<IconGroupVo> voList = iconGroupMapper.findVoList(po);
        trainingProxy.fillUserNamesByIds(voList);
        return (Page) voList;

    }

    @Override
    public int checkMultiCountByUnique(IconGroup po) {
        return iconGroupMapper.checkMultiCountByUnique(po);
    }

    @Override
    public void increaseTotal(IconGroup po) {
        iconGroupMapper.increaseTotal(po);
    }
}