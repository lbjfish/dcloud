package com.sida.dcloud.operation.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.operation.dao.TagLibMapper;
import com.sida.dcloud.operation.po.CompanyRelTag;
import com.sida.dcloud.operation.po.TagLib;
import com.sida.dcloud.operation.po.TagLib;
import com.sida.dcloud.operation.service.TagLibService;
import com.sida.dcloud.operation.vo.TagLibVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TagLibServiceImpl extends BaseServiceImpl<TagLib> implements TagLibService {
    private static final Logger LOG = LoggerFactory.getLogger(TagLibServiceImpl.class);

    @Autowired
    private TagLibMapper tagLibMapper;

    @Override
    public IMybatisDao<TagLib> getBaseDao() {
        return tagLibMapper;
    }

    @Override
    public Page<TagLibVo> findPageList(TagLib po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<TagLibVo> voList = tagLibMapper.findVoList(po);
        return (Page) voList;
    }

    @Override
    public List<TagLibVo> findAllList(TagLib po) {
        return tagLibMapper.findVoList(po);
    }

    @Override
    public List<CompanyRelTag> findAllRelList() {
        return tagLibMapper.findAllRelList();
    }

    @Override
    public Map<String, Map<String, String>> findRelMapGroupByCompany() {
        return tagLibMapper.findRelMapGroupByCompany();
    }
}