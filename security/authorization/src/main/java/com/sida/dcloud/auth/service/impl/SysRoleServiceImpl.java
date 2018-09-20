package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.service.SysResourceService;
import com.sida.dcloud.auth.service.SysRoleService;
import com.sida.dcloud.auth.dao.SysRoleMapper;
import com.sida.dcloud.auth.po.SysRole;
import com.sida.dcloud.auth.vo.RoleDTO;
import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.dcloud.auth.vo.SysRoleVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.BuildTree;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
    private static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysResourceService sysResourceService;

    @Override
    public IMybatisDao<SysRole> getBaseDao() {
        return sysRoleMapper;
    }

    @Override
    public List<SysRole> selectRolesByUserId(String userId) {
        return sysRoleMapper.selectRolesByUserId(userId);
    }

    @Override
    public void enableRoles(String roleIds) {
        roleIds = com.sida.xiruo.common.components.StringUtils.addSingleQuotes(roleIds);
        sysRoleMapper.enableRoles(roleIds);
    }

    @Override
    public void disableRoles(String roleIds) {
        roleIds = com.sida.xiruo.common.components.StringUtils.addSingleQuotes(roleIds);
        sysRoleMapper.disableRoles(roleIds);
    }

    @Override
    public Page<SysRole> findPageList(SysRole role) {
        PageHelper.startPage(role.getP(),role.getS());
        List<SysRole> list = sysRoleMapper.findList(role);
        return (Page) list;
    }

    @Override
    public SysRoleVo findOneVo(String roleId) {
        List<String> resourceIds = Lists.newArrayList();
        SysRoleVo vo = new SysRoleVo();
        SysRole po = sysRoleMapper.selectByPrimaryKey(roleId);
        BeanUtils.copyProperties(po,vo);
        List<SysResourceVo> resVoList = sysResourceService.findVoListByRoleId(roleId);
        resVoList = BuildTree.buildTree(resVoList);

        for (SysResourceVo resVo : resVoList){
            if (SecConstant.YES.equals(resVo.getIdentifier())){
                putChildren(resourceIds,resVo.getChildren(),resVo);
            }
        }
        vo.setResources(resVoList);
        vo.setResourceIds(resourceIds);
        return vo;
    }

    private void putChildren(List<String> resourceIds, List<SysResourceVo> children, SysResourceVo resourceVo) {
        if (children==null || children.size() <= 0){
            resourceIds.add(resourceVo.getId());
            return;
        }
        for (SysResourceVo vo : children){
            if (SecConstant.YES.equals(vo.getIdentifier())){
                putChildren(resourceIds,vo.getChildren(),vo);
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int saveRole(SysRoleVo param) {
        if (super.saveOrUpdate(param) > 0){
            return sysResourceService.saveRoleResource(param.getId(),param.getResourceIds());
        }
        return -1;
    }

    @Override
    public List<RoleDTO> findRoleListByUserId(String userId) {
        if (BlankUtil.isEmpty(userId)){
            return null;
        }
        return sysRoleMapper.findRoleListByUserId(userId);
    }
}