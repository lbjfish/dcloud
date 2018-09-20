package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.po.SysField;
import com.sida.dcloud.auth.po.SysUserHiddenField;
import com.sida.dcloud.system.dao.SysFieldMapper;
import com.sida.dcloud.system.service.SysFieldService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysFieldServiceImpl extends BaseServiceImpl<SysField> implements SysFieldService {
    private static final Logger logger = LoggerFactory.getLogger(SysFieldServiceImpl.class);

    @Autowired
    private SysFieldMapper sysFieldMapper;

    @Override
    public IMybatisDao<SysField> getBaseDao() {
        return sysFieldMapper;
    }

    @Override
    public List<SysUserHiddenField> findFields(String userId, String pageCode, List<String> nameList, List<String> codeList) {
        List<SysField> list = sysFieldMapper.findFields(pageCode,nameList,codeList);
        List<SysUserHiddenField> hiddenFields = Lists.newArrayList();
        if (list!=null && list.size()>0){
            for (SysField field : list){
                SysUserHiddenField hiddenField = new SysUserHiddenField();
                hiddenField.setUserId(userId);
                hiddenField.setHiddenFieldId(field.getId());
                hiddenFields.add(hiddenField);
            }
        }
        return hiddenFields;
    }
}