package com.sida.dcloud.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.activity.dao.SysUserActivityMapper;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.po.SysUserActivity;
import com.sida.dcloud.activity.service.SysUserActivityService;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.dcloud.activity.vo.SysUserActivityVo;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
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
public class SysUserActivityServiceImpl extends BaseServiceImpl<SysUserActivity> implements SysUserActivityService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserActivityServiceImpl.class);

    @Autowired
    private SysUserActivityMapper sysUserActivityMapper;

    @Override
    public IMybatisDao<SysUserActivity> getBaseDao() {
        return sysUserActivityMapper;
    }

    @RedisLock
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateUserList(List<SysUser> userList) {
        userList.forEach(user -> updateUser(user));
        return 0;
    }

    @RedisLock
    @Override
    public int updateUser(SysUser user) {
        return sysUserActivityMapper.updateByUserPrimaryKey(user);
    }

    @Override
    public Page<SysUserActivityVo> findPageList(SysUserActivity po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<SysUserActivityVo> voList = sysUserActivityMapper.selectVoList(po);
        return (Page) voList;
    }

    @Override
    public int inertDto(Map<String, String> map) {
        return sysUserActivityMapper.insertDto(map);
    }

    @Override
    public int updateMobile(Map<String, String> map) {
        return sysUserActivityMapper.updateMobile(map);
    }

    @Override
    public int updateUserInfo(Map<String, String> map) {
        return sysUserActivityMapper.updateUserInfo(map);
    }
}