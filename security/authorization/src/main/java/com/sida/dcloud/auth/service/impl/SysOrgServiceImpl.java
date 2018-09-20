package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysOrgMapper;
import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.service.SysOrgService;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BuildTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrg> implements SysOrgService {
    private static final Logger logger = LoggerFactory.getLogger(SysOrgServiceImpl.class);

    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Override
    public IMybatisDao<SysOrg> getBaseDao() {
        return sysOrgMapper;
    }

    @Override
    public Object findTree() {
        SysOrg condition = new SysOrg();
        condition.setOrderField(SecConstant.SORT);
        condition.setOrderString(SecConstant.ASC);
        condition.setDisable(false);
        condition.setDeleteFlag(false);
        List<SysOrg> list = sysOrgMapper.selectByCondition(condition);
        return BuildTree.buildTree(list);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(String id) {
        sysOrgMapper.deleteEmployeePositionRela(id);
        sysOrgMapper.deleteById(id);
    }

    /**
     * 根据组织id获取对应的组织信息
     *
     * @param id
     * @return
     * @author xieguopei
     * @date 2017/09/05
     */
    @Override
    public Object selectOrgMesById(String id) {
        SysOrg sysOrg;

        sysOrg = sysOrgMapper.selectByPrimaryKey(id);

        return sysOrg;
    }
}