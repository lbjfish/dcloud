package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysResource;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.po.SysUserHiddenField;
import com.sida.dcloud.auth.vo.PageResourceDTO;
import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.dcloud.auth.vo.UserHiddenColumnDTO;
import com.sida.dcloud.system.service.SysFieldService;
import com.sida.dcloud.system.service.SysResourceService;
import com.sida.dcloud.system.service.SysUserHiddenFieldService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.BuildTree;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sysResource")
@Api(description = "资源管理api")
public class SysResourceController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysResourceController.class);

    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private SysFieldService sysFieldService;
    @Autowired
    private SysUserHiddenFieldService sysUserHiddenFieldService;

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ApiOperation(value = "初始化资源")
    public Object init() {
        sysResourceService.init();
        return toResult();
    }

    private void addDefault(List<SysResource> resList, SysResource res) {
        addDefault(resList,res,null);
    }

    private void addDefault(List<SysResource> resList, SysResource res,SysResource parent) {
        PoDefaultValueGenerator.putDefaultValue(res);
        if (parent!=null){
            res.setParentId(parent.getId());
            res.setPath(parent.getPath() + res.getId() + ".");
        }else {
            res.setParentId(null);
            res.setPath(res.getId() + ".");
        }
        resList.add(res);
    }

    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ApiOperation(value = "获取资源树")
    public Object tree() {
        SysResource condition = new SysResource();
        condition.setDisable(false);
        condition.setDeleteFlag(false);
        condition.setOrderField(SecConstant.SORT);
        condition.setOrderString(SecConstant.ASC);
        List<SysResource> res = sysResourceService.selectByCondition(condition);
        List<SysResourceVo> voList = Lists.newArrayList();
        for (SysResource po :res){
            SysResourceVo vo = new SysResourceVo();
            org.springframework.beans.BeanUtils.copyProperties(po,vo);
            voList.add(vo);
        }
        voList = BuildTree.buildTree(voList);
        return toResult(voList);
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新资源")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") SysResource param) {
        if (StringUtils.isBlank(param.getId())){
            PoDefaultValueGenerator.putDefaultValue(param);
            if (StringUtils.isNotBlank(param.getParentId())){
                SysResource parent = sysResourceService.selectByPrimaryKey(param.getParentId());
                if (parent!=null){
                    param.setPath(parent.getPath());
                }
            }
            param.setPath((param.getPath()==null?"":param.getPath()) + param.getId() + ".");
            sysResourceService.insertSelective(param);
        }else {
            PoDefaultValueGenerator.putDefaultValue(param);
            sysResourceService.updateByPrimaryKeySelective(param);
        }
        return toResult();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "删除资源信息")
    public Object delete(@PathVariable("id") String id) {
        sysResourceService.deleteById(id);
        return toResult();
    }

    @RequestMapping(value = "/findPageResource", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色候选列表")
    public Object findPageResourceByPageCode(@RequestParam @ApiParam("页面code（前端控件id）") String pageCode) {
        //获取当前登录用户
        SysUser user = getUser();
        String userId = user==null?null:user.getId();
        //获取该页面下所有的按钮与字段资源
        PageResourceDTO resourceMap = sysResourceService.findPageResourceMapByPageCode(pageCode,userId);
        return toResult(resourceMap);
    }

    @RequestMapping(value = "/saveHiddenColumn", method = RequestMethod.POST)
    @ApiOperation(value = "设置用户隐藏列")
    public Object saveHiddenColumn(@RequestBody @ApiParam("二选一，隐藏列名称数组") UserHiddenColumnDTO param) {
        //若传空数组则表示恢复用户在该页面的设置。即删除隐藏列
        //获取当前登录用户id
        String userId = LoginManager.getCurrentUserId();
        //新增前先删除原有的隐藏列
        if (BlankUtil.isEmpty(param.getNameList()) && BlankUtil.isEmpty(param.getCodeList())){
            sysUserHiddenFieldService.deleteByUserAndPageCode(userId,param.getPageCode());
            return toResult();
        }
        //根据名称|code列表 获取相应的字段资源
        List<SysUserHiddenField> hiddenFields = sysFieldService.findFields(userId,param.getPageCode(),param.getNameList(),param.getCodeList());
        sysUserHiddenFieldService.insertMany(userId,param.getPageCode(),hiddenFields);
        return toResult();
    }


    /************************************以下是向微服务提供的接口**********************************/
    @RequestMapping(value = "/findListByRoleCode", method = RequestMethod.POST)
    @ApiOperation(value = "根据角色信息获取对应资源信息")
    public Object findListByRoleCode(@RequestParam(required = false) @ApiParam("角色id 二选一") String roleId,
                                     @RequestParam(required = false) @ApiParam("角色编码 二选一") String roleCode) {
//        if (StringUtils.isBlank(roleId) && StringUtils.isBlank(roleCode)){
//            return toResult();
//        }
        List<SysResourceVo> list = sysResourceService.findListByRoleCode(roleId,roleCode);
        return toResult(list);
    }
}