package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.dao.HonoredGuestMapper;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.service.HonoredGuestService;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HonoredGuestServiceImpl extends BaseServiceImpl<HonoredGuest> implements HonoredGuestService {
    private static final Logger logger = LoggerFactory.getLogger(HonoredGuestServiceImpl.class);

    @Autowired
    private HonoredGuestMapper honoredGuestMapper;

    @Override
    public IMybatisDao<HonoredGuest> getBaseDao() {
        return honoredGuestMapper;
    }

    @Override
    public Page<HonoredGuestVo> findPageList(HonoredGuest po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<HonoredGuestVo> voList = honoredGuestMapper.findVoList(po);
        return (Page) voList;
    }

    @Override
    public int checkMultiCountByUnique(HonoredGuest po) {
        return honoredGuestMapper.checkMultiCountByUnique(po);
    }

    @Override
    public List<HonoredGuestVo> findVoListByScheduleId(String scheduleId) {
        return honoredGuestMapper.findVoListByScheduleId(scheduleId);
    }
}