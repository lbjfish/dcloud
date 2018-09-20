package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.vo.RoleDTO;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.dcloud.system.service.SysAppFunctionGroupService;
import com.sida.dcloud.system.vo.SysAppFunctionGroupVo;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sysAppFunctionGroup")
@Api(description = "工作区相关")
public class SysAppFunctionGroupController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysAppFunctionGroupController.class);

    @Autowired
    private SysAppFunctionGroupService sysAppFunctionGroupService;


    @RequestMapping(value = "/getFunction", method = RequestMethod.GET)
    @ApiOperation(value = "获取角色app功能信息")
    public Object getFunction(@ApiParam("应用id")@RequestParam(value = "appId") String appId) {
        SysUserVo sysUserVo= LoginManager.getUser();
        if(BlankUtil.isEmpty(sysUserVo.getRoleList())){
            throw new ServiceException("10009","未获取到登录人角色信息");
        }
        List<String> roleIds=new ArrayList<>();
        for (RoleDTO roleDTO:sysUserVo.getRoleList()){
            roleIds.add(roleDTO.getRoleId());
        }
        List<SysAppFunctionGroupVo> sysAppFunctionGroupVoList=sysAppFunctionGroupService.findSysAppFunctionGroupList(appId,roleIds);
        Map returnMap=new HashMap<>();
        returnMap.put("funList",sysAppFunctionGroupVoList);
        return toResult(returnMap);
    }
}