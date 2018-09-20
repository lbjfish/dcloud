package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.system.dao.SysAreaMapper;
import com.sida.dcloud.system.po.SysArea;
import com.sida.dcloud.system.service.SysAreaService;
import com.sida.dcloud.system.vo.SysAreaVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysAreaServiceImpl extends BaseServiceImpl<SysArea> implements SysAreaService {
    private static final Logger logger = LoggerFactory.getLogger(SysAreaServiceImpl.class);

    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Override
    public IMybatisDao<SysArea> getBaseDao() {
        return sysAreaMapper;
    }

    @Override
    public Page<SysAreaVo> findPageList(SysArea area) {
        PageHelper.startPage(area.getP(),area.getS());
        List<SysAreaVo> voList = sysAreaMapper.findVoList(area);
        return (Page) voList;

    }

    @Override
    public Map<String, SysArea> findMapByIdIn(List<String> ids) {
        Map<String,SysArea> map = new HashMap<>();
        List<SysArea> list = sysAreaMapper.findByIdIn(ids);
        if (BlankUtil.isNotEmpty(list)){
            for (SysArea area : list){
                map.put(area.getId(),area);
            }
        }
        return map;
    }

    @Override
    public void updateWithOrgs(List<SysOrg> orgList) {
        if (BlankUtil.isNotEmpty(orgList)){
            sysAreaMapper.updateAreaByModifyOrg(orgList);
        }
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysAreaMapper.deleteByIds(ids);
    }

    @Override
    public void insertWithOrgs(List<SysOrg> orgList) {
        if(BlankUtil.isNotEmpty(orgList)) {
            sysAreaMapper.insertWithOrgs(orgList);
        }
    }

    @Override
    public void removeAll() {
        sysAreaMapper.removeAll();
    }

    @Override
    public void insertWithAllOrgs() {
        sysAreaMapper.insertWithAllOrgs();
    }
}