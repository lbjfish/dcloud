package com.sida.dcloud.training.service.impl;

import com.sida.dcloud.training.dao.PraticeTrackMapper;
import com.sida.dcloud.training.po.PraticeTrack;
import com.sida.dcloud.training.service.PraticeTrackService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PraticeTrackServiceImpl extends BaseServiceImpl<PraticeTrack> implements PraticeTrackService {
    private static final Logger logger = LoggerFactory.getLogger(PraticeTrackServiceImpl.class);

    @Autowired
    private PraticeTrackMapper praticeTrackMapper;

    @Override
    public IMybatisDao<PraticeTrack> getBaseDao() {
        return praticeTrackMapper;
    }
}