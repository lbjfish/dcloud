package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.vo.InitStoreDTO;
import com.sida.dcloud.auth.vo.InitSystemDTO;
import com.sida.dcloud.system.service.SysCommonService;
import com.sida.dcloud.system.service.SysEmployeeService;
import com.sida.dcloud.system.service.SysOrgService;
import com.sida.dcloud.system.service.SysPositionService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.MapToBean;
import com.sida.xiruo.xframework.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by wuzhenwei
 * 2017/11/30.
 */
@RestController
@RequestMapping("init")
@Api(description = "初始化相关")
public class InitController extends BaseController {

    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysCommonService sysCommonService;
    @Autowired
    private SysEmployeeService sysEmployeeService;
    @Autowired
    private SysPositionService sysPositionService;

    @PostMapping("/firstInitOrg")
    @ApiOperation("首次初始化片区门店")
    public Object firstInitOrg() {
        sysOrgService.firstInitOrg();
        return toResult();
    }

    @PostMapping("/incrSyncInitOrg")
    @ApiOperation("增量同步触发刷新片区门店")
    public Object incrSyncInitOrg(List<SysOrg> orgList) {
        return toResult();
    }

    @PostMapping("/initSystem")
    @ApiOperation("初始化系统")
    public Object initSystem(@RequestBody @ApiParam("JSON参数") InitSystemDTO initSystemDTO) {
        return toResult();
    }
}