package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.dao.ConsultationInfoMapper;
import com.sida.dcloud.activity.po.ActivityOrderGoods;
import com.sida.dcloud.activity.po.ConsultationInfo;
import com.sida.dcloud.activity.service.ConsultationInfoService;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.ConsultationInfoVo;
import com.sida.dcloud.system.dto.SysRegionLayerDto;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ConsultationInfoServiceImpl extends BaseServiceImpl<ConsultationInfo> implements ConsultationInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(ConsultationInfoServiceImpl.class);

    @Autowired
    private ConsultationInfoMapper consultationInfoMapper;

    @Override
    public IMybatisDao<ConsultationInfo> getBaseDao() {
        return consultationInfoMapper;
    }

    @Override
    public Page<ConsultationInfoVo> findPageList(ConsultationInfo po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<ConsultationInfoVo> voList = consultationInfoMapper.findVoList(po);
        return (Page) voList;
    }

    @Override
    public String findCompanyIdsByNoteId(String noteId) {
        return consultationInfoMapper.findCompanyIdsByNoteId(noteId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int batchInsert(List<ConsultationInfo> list) {
        return consultationInfoMapper.batchInsert(list);
    }
}