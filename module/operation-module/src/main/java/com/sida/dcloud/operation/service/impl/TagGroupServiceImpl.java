package com.sida.dcloud.operation.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.operation.dao.TagGroupMapper;
import com.sida.dcloud.operation.po.TagGroup;
import com.sida.dcloud.operation.service.TagGroupService;
import com.sida.dcloud.operation.vo.TagGroupVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagGroupServiceImpl extends BaseServiceImpl<TagGroup> implements TagGroupService {
    private static final Logger logger = LoggerFactory.getLogger(TagGroupServiceImpl.class);

    @Autowired
    private TagGroupMapper tagGroupMapper;

    @Override
    public IMybatisDao<TagGroup> getBaseDao() {
        return tagGroupMapper;
    }

    @Override
    public Page<TagGroupVo> findPageList(TagGroup po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<TagGroupVo> voList = tagGroupMapper.findVoList(po);
        return (Page) voList;
    }

    @Override
    public List<TagGroupVo> findAllList(TagGroup po) {
        return tagGroupMapper.findVoList(po);
    }
}