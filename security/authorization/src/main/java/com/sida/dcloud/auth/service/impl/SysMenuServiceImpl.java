package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysMenuMapper;
import com.sida.dcloud.auth.po.SysMenu;
import com.sida.dcloud.auth.service.SysMenuService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService {
    private static final Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public IMybatisDao<SysMenu> getBaseDao() {
        return sysMenuMapper;
    }
}