package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysRegionMapper;
import com.sida.dcloud.auth.po.SysRegion;
import com.sida.dcloud.auth.service.SysRegionService;
import com.sida.dcloud.auth.vo.RegionTreeDTO;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BuildTree;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRegionServiceImpl extends BaseServiceImpl<SysRegion> implements SysRegionService {
    private static final Logger logger = LoggerFactory.getLogger(SysRegionServiceImpl.class);

    @Autowired
    private SysRegionMapper sysRegionMapper;

    @Override
    public IMybatisDao<SysRegion> getBaseDao() {
        return sysRegionMapper;
    }

    @Override
    public List<RegionTreeDTO> findTree(){
        SysRegion condition = new SysRegion();
        condition.setOrderField("sort");
        condition.setOrderString("asc");
        condition.setDeleteFlag(false);
        condition.setDisable(false);
        List<SysRegion> list = sysRegionMapper.selectByCondition(condition);
        List<RegionTreeDTO> dtoList = Lists.newArrayList();
        for (SysRegion region : list){
            RegionTreeDTO dto = new RegionTreeDTO();
            dto.setId(region.getId());
            dto.setName(region.getName());
            dto.setCode(region.getCode());
            dto.setLevel(region.getLevel());
            dto.setSort(region.getSort());
            dto.setParentId(region.getParentId());
            dtoList.add(dto);
        }
        return BuildTree.buildTree(dtoList);
    }

    @Override
    public int deleteById(String id){
        return sysRegionMapper.deleteById(id);
    }
}