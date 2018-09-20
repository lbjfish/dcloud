package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.common.SysEnums;
import com.sida.dcloud.auth.dao.*;
import com.sida.dcloud.auth.po.*;
import com.sida.dcloud.auth.service.SysResourceService;
import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceService {
    private static final Logger logger = LoggerFactory.getLogger(SysResourceServiceImpl.class);

    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysButtonMapper sysButtonMapper;
    @Autowired
    private SysFieldMapper sysFieldMapper;

    @Override
    public IMybatisDao<SysResource> getBaseDao() {
        return sysResourceMapper;
    }

    @Override
    public List<SysResourceVo> findVoListByRoleId(String roleId) {
        List<SysResourceVo> voList = Lists.newArrayList();

        //获取所有资源列表（type='page' 和 type='button' ）
        List<String> typeList = Lists.newArrayList();
        typeList.add(SysEnums.ResourceTypeEnums.PAGE.getName());
        typeList.add(SysEnums.ResourceTypeEnums.BUTTON.getName());
        //id字段使用path值
        List<SysResource> resList = sysResourceMapper.selectByTypeIn(typeList);

        Map<String,SysResource> resMap = new HashMap<>();
        for (SysResource res : resList){
            resMap.put(res.getId(),res);
        }

        //根据角色id获取已配置的资源
        SysRoleResource condition = new SysRoleResource();
        condition.setRoleId(roleId);
        List<SysRoleResource> roleResourceList = sysRoleResourceMapper.selectByCondition(condition);

        Map<String,Boolean> isExistChildren = new HashMap<>();

        //遍历获取已配置的资源id Map
        Map<String,SysResource> roleResourceMap = new HashMap<>();
        for (SysRoleResource roleResource : roleResourceList){
            roleResourceMap.put(roleResource.getResourceId(),resMap.get(roleResource.getResourceId()));
            SysResource temp = resMap.get(roleResource.getResourceId());
            if ( temp != null && StringUtils.isNotBlank(temp.getParentId())
                    && !isExistChildren.containsKey(temp.getParentId())){
                isExistChildren.put(temp.getParentId(),true);
            }
        }

        Map<String,String> parentIdMap = new HashMap<>();
        for (SysResource res : resList){
            //如果不是顶层菜单 && 属于按钮层级 && parentId未创建过默认节点
            if (StringUtils.isNotBlank(res.getParentId()) && SysEnums.ResourceTypeEnums.BUTTON.getName().equals(res.getType())
                    && !parentIdMap.containsKey(res.getParentId())){
                SysResourceVo defaultVo = new SysResourceVo();
                defaultVo.setId(SecConstant.DEFAULT_+res.getParentId());
                defaultVo.setName("查询");
                defaultVo.setCode("disableAll");
                defaultVo.setParentId(res.getParentId());
                defaultVo.setSort(0);
                defaultVo.setValue("value");
                defaultVo.setType(SysEnums.ResourceTypeEnums.BUTTON.getName());
                defaultVo.setIdentifier(SecConstant.NO);
//                if (!isExistChildren.containsKey(res.getParentId()) && roleResourceMap.containsKey(res.getParentId())){
                if (roleResourceMap.containsKey(res.getParentId())){
                    defaultVo.setIdentifier(SecConstant.YES);
                }
                parentIdMap.put(res.getParentId(),res.getName());
                voList.add(defaultVo);
            }

            SysResourceVo vo = new SysResourceVo();
            BeanUtils.copyProperties(res,vo);
            if (roleResourceMap.containsKey(res.getId())){
                vo.setIdentifier(SecConstant.YES);
            }else {
                vo.setIdentifier(SecConstant.NO);
            }
            voList.add(vo);
        }
        return voList;
    }

    @Override
    @Transactional
    public int saveRoleResource(String roleId, List<String> resourceIds) {
        if (resourceIds!=null){
            //逻辑删除所有菜单、按钮权限
            List<String> typeList = Lists.newArrayList();
            typeList.add(SysEnums.ResourceTypeEnums.PAGE.getName());
            typeList.add(SysEnums.ResourceTypeEnums.BUTTON.getName());
            sysRoleResourceMapper.deleteByRoleIdAndType(roleId,typeList);

            List<String> parentIdList = Lists.newArrayList();
            //根据前端返回ids(前端直返回根节点id，只有当一个父节点下全部子节点被选中该父节点才会有id返回到后端，所以要后端默认给选中的节点添加上父级id)
            //添加上菜单、按钮权限

            Iterator<String> it = resourceIds.iterator();
            while(it.hasNext()){
                String resourceId = it.next();
                if (resourceId.startsWith(SecConstant.DEFAULT_)){
                    parentIdList.add(resourceId.replace(SecConstant.DEFAULT_,""));
                    it.remove();
                }
            }

            resourceIds.addAll(parentIdList);


//            for (String resourceId : resourceIds){
//
//            }
            List<SysRoleResource> rrList = Lists.newArrayList();
            List<String> resIdList = Lists.newArrayList();
            List<SysResource> resList = sysResourceMapper.selectByIds(resourceIds);
            for (SysResource res : resList){
                String[] str = res.getPath().split("\\.");
                for(int i = 0; i < str.length; i++){
                    if (!resIdList.contains(str[i])){
                        SysRoleResource rr = new SysRoleResource();
                        resIdList.add(str[i]);
                        rr.setRoleId(roleId);
                        rr.setResourceId(str[i]);
                        rrList.add(rr);
                    }
                }
            }
            if (rrList.size()>0){
                sysRoleResourceMapper.addManyRoleResource(rrList);
//                sysRoleResourceMapper.deleteByRoleIdAndParentId(roleId,parentIdList);
            }
        }
        return 1;
    }

    private void getRoleResList(List<SysRoleResource> roleResList, String roleId, List<SysResourceVo> resources) {
        if (resources==null || resources.size()<=0){
            return;
        }
        for (SysResourceVo vo : resources){
            if (SecConstant.YES.equals(vo.getIdentifier())){
                SysRoleResource rr = new SysRoleResource();
                rr.setRoleId(roleId);
                rr.setResourceId(vo.getId());
                roleResList.add(rr);
            }
            getRoleResList(roleResList,roleId,vo.getChildren());
        }
    }


    @Override
    public int insertAnotherInfoByType(SysResource resource, String type) {
        //保存菜单
        if (SysEnums.ResourceTypeEnums.PAGE.getName().equals(type)){
            SysMenu menu = new SysMenu();
            BeanUtils.copyProperties(resource,menu);
            return sysMenuMapper.insertSelective(menu);
        }

        //保存按钮
        else if(SysEnums.ResourceTypeEnums.BUTTON.getName().equals(type)){
            SysButton button = new SysButton();
            BeanUtils.copyProperties(resource,button);
            return sysButtonMapper.insertSelective(button);
        }

        //保存字段
        else if (SysEnums.ResourceTypeEnums.FIELD.getName().equals(type)){
            SysField field = new SysField();
            BeanUtils.copyProperties(resource,field);
            return sysFieldMapper.insertSelective(field);
        }

        else{
            return -1;
        }
    }

    @Override
    public int deleteById(String id) {
        return sysResourceMapper.deleteById(id);
    }
}