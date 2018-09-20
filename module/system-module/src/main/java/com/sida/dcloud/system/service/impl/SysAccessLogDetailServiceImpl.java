package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysAccessLogDetailMapper;
import com.sida.dcloud.system.po.SysAccessLogDetail;
import com.sida.dcloud.system.service.SysAccessLogDetailService;
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
public class SysAccessLogDetailServiceImpl extends BaseServiceImpl<SysAccessLogDetail> implements SysAccessLogDetailService {
    private static final Logger logger = LoggerFactory.getLogger(SysAccessLogDetailServiceImpl.class);

    @Autowired
    private SysAccessLogDetailMapper sysAccessLogDetailMapper;

    @Override
    public IMybatisDao<SysAccessLogDetail> getBaseDao() {
        return sysAccessLogDetailMapper;
    }

    @Override
    public Page<SysAccessLogDetail> pageList(SysAccessLogDetail vo) {
        PageHelper.startPage(vo.getP(), vo.getS());
        List<SysAccessLogDetail> list = sysAccessLogDetailMapper.selectByCondition(vo);
        return (Page<SysAccessLogDetail>) list;
    }

    @Override
    public Page<SysAccessLogDetail> selecAccessLogstByDate(int p, int s, String startTime, String endTime) {
        PageHelper.startPage(p, s);
        List<SysAccessLogDetail> list =sysAccessLogDetailMapper.selecAccessLogstByDate(startTime,endTime);
        return (Page<SysAccessLogDetail>) list;
    }
}