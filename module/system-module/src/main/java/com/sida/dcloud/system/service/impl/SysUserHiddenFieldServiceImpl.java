package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.po.SysUserHiddenField;
import com.sida.dcloud.system.dao.SysUserHiddenFieldMapper;
import com.sida.dcloud.system.service.SysUserHiddenFieldService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysUserHiddenFieldServiceImpl extends BaseServiceImpl<SysUserHiddenField> implements SysUserHiddenFieldService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserHiddenFieldServiceImpl.class);

    @Autowired
    private SysUserHiddenFieldMapper sysUserHiddenFieldMapper;

    @Override
    public IMybatisDao<SysUserHiddenField> getBaseDao() {
        return sysUserHiddenFieldMapper;
    }

    @Override
    @Transactional
    public int insertMany(String userId, String pageCode, List<SysUserHiddenField> hiddenFields) {
        if (BlankUtil.isNotEmpty(hiddenFields)){
            //先物理删除该用户该页面下关联关系
            sysUserHiddenFieldMapper.deleteByUserIdAndPageCode(userId,pageCode);
            //批量插入用户-隐藏字段关联关系
            return sysUserHiddenFieldMapper.insertMany(hiddenFields);
        }
        return 0;
    }

    @Override
    public int deleteByUserAndPageCode(String userId, String pageCode) {
        return sysUserHiddenFieldMapper.deleteByUserIdAndPageCode(userId,pageCode);
    }
}