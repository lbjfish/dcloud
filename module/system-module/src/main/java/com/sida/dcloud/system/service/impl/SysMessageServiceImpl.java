package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.system.dao.SysMessageMapper;
import com.sida.dcloud.system.po.SysMessage;
import com.sida.dcloud.system.service.SysMessageService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessage> implements SysMessageService {
    private static final Logger logger = LoggerFactory.getLogger(SysMessageServiceImpl.class);

    @Autowired
    private SysMessageMapper sysMessageMapper;

    @Override
    public IMybatisDao<SysMessage> getBaseDao() {
        return sysMessageMapper;
    }

    @Override
    public Page<SysMessage> findPageList(SysMessage param) {
        //设置用户id
        if (BlankUtil.isEmpty(param.getUserId())){
            param.setUserId(LoginManager.getCurrentUserId());
        }
        //过滤逻辑删
        param.setDeleteFlag(false);
        param.setDisable(false);
        //设置默认排序
        if (StringUtils.isBlank(param.getOrderField())){
            param.setOrderField(SecConstant.CREATED_AT);
            param.setOrderString(SecConstant.DESC);
        }
        PageHelper.startPage(param.getP(),param.getS());
        List<SysMessage> list = sysMessageMapper.selectByCondition(param);
        return (Page) list;
    }

    @Override
    @Transactional
    public void deleteByIds(List<String> ids) {
        if (BlankUtil.isNotEmpty(ids)){
            sysMessageMapper.deleteByIds(ids);
        }
    }

    @Override
    @Transactional
    public void removeByIds(List<String> ids) {
        if (BlankUtil.isNotEmpty(ids)){
            sysMessageMapper.removeByIds(ids);
        }
    }

    @Override
    @Transactional
    public void removeByProcInstId(String procId) {
        if (BlankUtil.isNotEmpty(procId)){
            sysMessageMapper.removeByProcInstId(procId);
        }
    }

    @Override
    @Transactional
    public void batchSave(List<SysMessage> list) {
        if (BlankUtil.isNotEmpty(list)){
            sysMessageMapper.batchInsert(list);
        }
    }

    @Override
    public Object readMessage(String id) {
        if (BlankUtil.isNotEmpty(id)){
            SysMessage update = new SysMessage();
            update.setId(id);
            update.setIsRead(1);
            PoDefaultValueGenerator.putDefaultValue(update);
            sysMessageMapper.updateByPrimaryKeySelective(update);
        }
        return null;
    }
}