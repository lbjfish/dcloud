package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.client.TrainingProxy;
import com.sida.dcloud.training.dao.IconInfoMapper;
import com.sida.dcloud.training.dto.GroupCountDto;
import com.sida.dcloud.training.po.IconInfo;
import com.sida.dcloud.training.service.IconInfoService;
import com.sida.dcloud.training.vo.IconInfoVo;
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
public class IconInfoServiceImpl extends BaseServiceImpl<IconInfo> implements IconInfoService {
    private static final Logger logger = LoggerFactory.getLogger(IconInfoServiceImpl.class);

    @Autowired
    private IconInfoMapper iconInfoMapper;
    @Autowired
    private TrainingProxy trainingProxy;

    @Override
    public IMybatisDao<IconInfo> getBaseDao() {
        return iconInfoMapper;
    }

    @Override
    public int selectIconInfoSizeByGroupIds(String groupIds) {
        return iconInfoMapper.selectIconInfoSizeByGroupIds(groupIds);
    }

    @Override
    public Page<IconInfoVo> findPageList(IconInfo po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<IconInfoVo> voList = iconInfoMapper.findVoList(po);
        trainingProxy.fillUserNamesByIds(voList);
        return (Page) voList;

    }

    @Override
    public int checkMultiCountByUnique(IconInfo po) {
        return iconInfoMapper.checkMultiCountByUnique(po);
    }

    @Override
    public List<GroupCountDto> findRemoveCountGroup(String stringIds) {
        return iconInfoMapper.findRemoveCountGroup(stringIds.replaceAll("^|$|(?=,[^,]+?)|(?<=[^,]+?,)", "'"));
    }
}