package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.common.SysEnums;
import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.vo.SysOrgVo;
import com.sida.dcloud.system.po.SysArea;
import com.sida.dcloud.system.service.SysAreaService;
import com.sida.dcloud.system.service.SysOrgService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.BuildTree;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sysArea")
@Api(description = "片区管理api")
public class SysAreaController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysAreaController.class);

    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysOrgService sysOrgService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查询片区列表")
    public Object list(@RequestBody @ApiParam("JSON参数") SysArea param) {
        Object object = sysAreaService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新片区")
    public Object update(@RequestBody @ApiParam("JSON参数") SysArea param) {
        //片区只提供部分信息的修改接口
        if (BlankUtil.isNotEmpty(param.getId())){
            //code与name不允许修改
            param.setCode(null);
            param.setName(null);
            sysAreaService.saveOrUpdate(param);
        }
        return toResult();
    }

    @RequestMapping(value = "/enableAreas", method = RequestMethod.POST)
    @ApiOperation(value = "批量启用片区")
    public Object enableAreas(@RequestBody @ApiParam("JSON参数")List<String> ids) {
        SysArea po = new SysArea();
        po.setStatus(false);
        sysAreaService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/disableAreas", method = RequestMethod.POST)
    @ApiOperation(value = "批量禁用片区")
    public Object disableAreas(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        SysArea po = new SysArea();
        po.setStatus(true);
        sysAreaService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/selectList", method = RequestMethod.POST)
    @ApiOperation(value = "获取片区候选列表")
    public Object selectList() {
        SysOrgVo condition = new SysOrgVo();
        condition.setAreaStatus(false);
        condition.setType(SysEnums.OrgTypeEnums.AREA.getType());
        condition.setOrgId(LoginManager.getCurrentOrgId());
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysOrg> areaList = sysOrgService.findListByVo(condition);
        //封装list为map，减少多余的字段
//        return toResult(BuildKeyValueMapUtil.change(areaList));
        return toResult(areaList);
    }


    @RequestMapping(value = "/selectTree", method = RequestMethod.POST)
    @ApiOperation(value = "获取片区-门店候选列表")
    public Object selectTree() {
        SysOrgVo condition = new SysOrgVo();
        //TODO:wuzhenwei 待营销模块确认，需要关联营销门店状态
        condition.setAreaStatus(false);
        condition.setStoreStatus(false);
        condition.setOrgId(LoginManager.getCurrentOrgId());
        List<Integer> typeIn = Lists.newArrayList();
        typeIn.add(SysEnums.OrgTypeEnums.AREA.getType());
        typeIn.add(SysEnums.OrgTypeEnums.STORE.getType());
        condition.setTypeIn(typeIn);
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysOrg> areaList = sysOrgService.findListByVo(condition);
        return toResult(BuildTree.buildTree(areaList));
    }

    @RequestMapping(value = "/findOrgsByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取组织")
    public Object findOrgsByIds(@RequestBody @ApiParam("JSON参数")List<String> ids) {
        Map<String,SysOrg> map = new HashMap<>();
        SysOrgVo condition = new SysOrgVo();
        condition.setOrgId(LoginManager.getCurrentOrgId());
        condition.setIdIn(ids);
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysOrg> orgList = sysOrgService.findOrgsByIds(condition);
        if (BlankUtil.isNotEmpty(orgList)){
            for (SysOrg org : orgList){
                map.put(org.getId(),org);
            }
        }
        return toResult(map);
    }

    @RequestMapping(value = "/findStoresByAreaId", method = RequestMethod.POST)
    @ApiOperation(value = "根据片区id获取门店信息")
    public Object findStoresByAreaId(@RequestBody @ApiParam("JSON参数")String areaId) {
        List<SysOrg> orgList = sysOrgService.findStoresByAreaId(areaId);
        return toResult(orgList);
    }

    @RequestMapping(value = "/findStoresMapByAreaIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据片区id集合获取门店信息map")
    public Object findStoresMapByAreaIds(@RequestBody @ApiParam("JSON参数")List<String> areaIds) {
        Map<String,List<SysOrg>> listMap = new HashMap<>();
        if (BlankUtil.isNotEmpty(areaIds)){
            for (String areaId : areaIds){
                listMap.put(areaId,sysOrgService.findStoresByAreaId(areaId));
            }
        }
        return toResult(listMap);
    }


    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "根据片区主键id获取片区")
    public Object findOne(@RequestBody @ApiParam("JSON参数")String id) {
        SysOrg org = sysOrgService.selectByPrimaryKey(id);
        return toResult(org);
    }
    @RequestMapping(value = "/findOneByAreaName", method = RequestMethod.POST)
    @ApiOperation(value = "根据片区主键id获取片区")
    public Object findOneByAreaName(@RequestBody @ApiParam("JSON参数")String areaName) {
        String orgId = LoginManager.getCurrentOrgId();
        SysArea condition = new SysArea();
        condition.setOrgId(orgId);
        condition.setName(areaName);
        condition.setDeleteFlag(false);
        condition.setDisable(false);
        List<SysArea> areaList = sysAreaService.selectByCondition(condition);
        SysArea sysArea = null;
        if(areaList!=null&&areaList.size()>0){
            sysArea = areaList.get(0);
        }
        return toResult(sysArea);
    }


}