package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.system.dao.SysAppFunctionGroupMapper;
import com.sida.dcloud.system.po.SysAppFunction;
import com.sida.dcloud.system.po.SysAppFunctionGroup;
import com.sida.dcloud.system.service.SysAppFunctionGroupService;
import com.sida.dcloud.system.vo.SysAppFunctionGroupVo;
import com.sida.dcloud.system.vo.SysAppFunctionVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysAppFunctionGroupServiceImpl extends BaseServiceImpl<SysAppFunctionGroup> implements SysAppFunctionGroupService {
    private static final Logger logger = LoggerFactory.getLogger(SysAppFunctionGroupServiceImpl.class);

    @Autowired
    private SysAppFunctionGroupMapper sysAppFunctionGroupMapper;

    @Override
    public IMybatisDao<SysAppFunctionGroup> getBaseDao() {
        return sysAppFunctionGroupMapper;
    }

    /**
     * 获取角色对应的app功能列表
     * @param appId  应用id
     * @param roleIds 角色id集合
     * @return
     */
    @Override
    public List<SysAppFunctionGroupVo> findSysAppFunctionGroupList(String appId, List<String> roleIds) {
        if(BlankUtil.isEmpty(appId)){
            throw new ServiceException("100008","应用id不能为空！");
        }
        if(BlankUtil.isEmpty(appId)){
            throw new ServiceException("100008","应用id不能为空！");
        }
         List<Map<String,Object>> list=sysAppFunctionGroupMapper.finSysAppFunctionGroupList(appId, roleIds);
         if(BlankUtil.isEmpty(list)){
             return null;
         }
         //遍历组装
        Map<String,List<SysAppFunction>> map=new HashMap<>();
        Map<String,SysAppFunctionGroup> groupMap=new HashMap<>();
        List<SysAppFunction>  sysAppFunctionList;
        SysAppFunctionVo sysAppFunctionVo;
        SysAppFunctionGroup sysAppFunctionGroup;
        for(Map tempMap:list){
            sysAppFunctionVo=new SysAppFunctionVo();
            sysAppFunctionVo.setId(tempMap.get("functionId")+"");
            sysAppFunctionVo.setAppId(appId);
            sysAppFunctionVo.setFunctionCode(tempMap.get("functionCode")+"");
            sysAppFunctionVo.setFunctionName(tempMap.get("functionName")+"");
            if(BlankUtil.isNotEmpty(tempMap.get("icon"))){
                sysAppFunctionVo.setIcon(tempMap.get("icon").toString());
            }
            if(BlankUtil.isNotEmpty(tempMap.get("herf"))){
                sysAppFunctionVo.setHerf(tempMap.get("herf").toString());
            }
            if(BlankUtil.isNotEmpty(tempMap.get("sourceUrl"))){
                sysAppFunctionVo.setSourceUrl(tempMap.get("sourceUrl").toString());
            }
            sysAppFunctionVo.setFunctionType(tempMap.get("functionType")+"");
            if(BlankUtil.isEmpty(map.get("groupId"))){
                sysAppFunctionList=new ArrayList<>();
                sysAppFunctionList.add(sysAppFunctionVo);
                map.put(tempMap.get("groupId")+"",sysAppFunctionList);
                sysAppFunctionGroup=new SysAppFunctionGroup();
                sysAppFunctionGroup.setId(tempMap.get("groupId")+"");
                sysAppFunctionGroup.setGroupName(tempMap.get("groupName")+"");
                sysAppFunctionGroup.setParentId(tempMap.get("parentId")+"");
                sysAppFunctionGroup.setAppId(tempMap.get("appId")+"");
                groupMap.put(tempMap.get("groupId")+"",sysAppFunctionGroup);
            }else {
                map.get("groupId").add(sysAppFunctionVo);
            }
        }
        SysAppFunctionGroupVo sysAppFunctionGroupVo;
        List<SysAppFunctionGroupVo>  sysAppFunctionGroupVoList=new ArrayList<>();
        for(String groupId:map.keySet()){
            sysAppFunctionGroupVo=new SysAppFunctionGroupVo();
            sysAppFunctionGroupVo.setId(groupId);
            sysAppFunctionGroupVo.setParentId(groupMap.get(groupId).getParentId());
            sysAppFunctionGroupVo.setGroupName(groupMap.get(groupId).getGroupName());
            sysAppFunctionGroupVo.setAppId(groupMap.get(groupId).getAppId());
            sysAppFunctionGroupVo.setFunctionItemList(map.get(groupId));
            sysAppFunctionGroupVoList.add(sysAppFunctionGroupVo);
        }
        //BuildTree.buildTree(sysAppFunctionGroupVoList);  //树形结构，后续扩展

        return sysAppFunctionGroupVoList;
    }
}