package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysPosition;
import com.sida.dcloud.auth.vo.RoleDTO;
import com.sida.dcloud.system.dao.SysRoleMapper;
import com.sida.dcloud.auth.po.SysRole;
import com.sida.dcloud.system.service.SysResourceService;
import com.sida.dcloud.system.service.SysRoleService;
import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.dcloud.auth.vo.SysRoleVo;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.common.util.ErrorCodeEnums;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.BuildTree;
import com.sida.xiruo.xframework.util.FormCheckUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void checkCode(String code) {
        if (StringUtils.isBlank(code)){
            throw new ServiceException(ErrorCodeEnums.CUSTOM.getCode(),"角色编码不能为空");
        }
        List<SysRole> roles = sysRoleMapper.selectByCode(code);
        if (roles !=null && roles.size() > 0){
            throw new ServiceException(ErrorCodeEnums.CUSTOM.getCode(),"角色编码已存在");
        }
    }

    @Override
    public SysRole selectByCode(String code) {
        List<SysRole> roles = sysRoleMapper.selectByCode(code);
        if (BlankUtil.isNotEmpty(roles)){
            return roles.get(0);
        }
        return new SysRole();
    }

    @Override
    public Map<String, SysRole> findRoleByCodes(List<String> roleCodes) {
        Map<String,SysRole> map = new HashMap<>();
        List<SysRole> roleList = sysRoleMapper.selectByCodes(roleCodes);
        if (BlankUtil.isNotEmpty(roleList)){
            for (SysRole role : roleList){
                map.put(role.getCode(),role);
            }
        }
        return map;
    }

    @Override
    public void removeAll() {
        sysRoleMapper.removeAll();
    }

    @Override
    public void batchInsert(List<SysRole> roleList) {
        if (BlankUtil.isNotEmpty(roleList)){
            sysRoleMapper.batchInsert(roleList);
        }
    }

    @Override
    public void insertWithPositions() {
        sysRoleMapper.insertWithPositions();
    }

    @Override
    public void insertWithPositions(List<SysPosition> list) {
        if (BlankUtil.isNotEmpty(list)){
            sysRoleMapper.insertWithPositionList(list);
        }
    }

    @Override
    public Map<String, SysRole> findMapByIds(List<String> list) {
        Map<String, SysRole> map = new HashMap<>();
        List<SysRole> roleList = sysRoleMapper.findByIds(list);
        if (BlankUtil.isNotEmpty(roleList)){
            for (SysRole po : roleList){
                map.put(po.getId(),po);
            }
        }
        return map;
    }

    @Override
    public void updateWithPositions(List<SysPosition> list) {
        if (BlankUtil.isNotEmpty(list)){
            sysRoleMapper.updateWithPositions(list);
        }
    }

    @Override
    public List<RoleDTO> findRoleListByUserId(String userId) {
        if (BlankUtil.isEmpty(userId)){
            return null;
        }
        return sysRoleMapper.findRoleListByUserId(userId);
    }

    @Override
    public boolean checkIfEmpLikeRoleScope(String empId, String roleScope) {
        FormCheckUtil.checkRequiredField(empId, "员工ID");
        FormCheckUtil.checkRequiredField(roleScope, "roleScope");
        int count = sysRoleMapper.checkIfEmpLikeRoleScope(empId, roleScope);
        return count>0;
    }

    @Override
    public boolean checkIfEmpInRoleScope(String empId, List<String> roles) {
        FormCheckUtil.checkRequiredField(empId, "员工ID");
        FormCheckUtil.checkRequiredField(roles, "roles");
        int count = sysRoleMapper.checkIfEmpInRoleScope(empId, roles);
        return count>0;
    }
}