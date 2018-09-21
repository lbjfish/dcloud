package com.sida.dcloud.activity.service.impl;

import com.sida.dcloud.activity.dao.CustomerActivitySignupNoteMapper;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.service.CustomerActivitySignupNoteService;
import com.sida.xiruo.po.common.TableMeta;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerActivitySignupNoteServiceImpl extends BaseServiceImpl<CustomerActivitySignupNote> implements CustomerActivitySignupNoteService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerActivitySignupNoteServiceImpl.class);

    @Autowired
    private CustomerActivitySignupNoteMapper customerActivitySignupNoteMapper;

    @Override
    public IMybatisDao<CustomerActivitySignupNote> getBaseDao() {
        return customerActivitySignupNoteMapper;
    }


    @Override
    public List<TableMeta> findTableMeta() {
        TableMeta tableMeta = new TableMeta();
        tableMeta.setTableSchema("activity");
        tableMeta.setTableName("customer_activity_signup_note");
        return customerActivitySignupNoteMapper.findTableMeta(tableMeta);
    }

    @Override
    public List<CustomerActivitySignupNote> findVoList(CustomerActivitySignupNote po) {
        return customerActivitySignupNoteMapper.findVoList(po);
    }

    @Override
    public int checkMultiCountByUnique(CustomerActivitySignupNote po) {
        return customerActivitySignupNoteMapper.checkMultiCountByUnique(po);
    }
}