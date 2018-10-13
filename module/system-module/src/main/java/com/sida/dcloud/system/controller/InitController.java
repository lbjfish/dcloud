package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.vo.InitStoreDTO;
import com.sida.dcloud.auth.vo.InitSystemDTO;
import com.sida.dcloud.system.service.*;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.MapToBean;
import com.sida.xiruo.xframework.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    private SysRegionService sysRegionService;
    @Autowired
    private SysEmployeeService sysEmployeeService;
    @Autowired
    private SysPositionService sysPositionService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/firstInitOrg")
    @ApiOperation("首次初始化片区门店")
    public Object firstInitOrg() {
        sysOrgService.firstInitOrg();
        return toResult();
    }

    @RequestMapping(value = "/updateSysRegionPinyin", method = RequestMethod.GET)
    @ApiOperation(value = "更新地区拼音字段")
    public Object updateSysRegionPinyin() {
        sysRegionService.updateSysRegionPinyin();
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

    /********************************/
    @GetMapping("/loadDicTree")
    @ApiOperation("加载所有字典")
    public Object loadDicTree() {
        List<Object> list = new ArrayList<>();
        redisUtil.getDicGroupMap().forEach((code, name) -> list.add(new HashMap<String, Object>() {
            {
                put("code", code);
                put("name", name);
                put("values", new ArrayList<Object>() {
                    {
                        redisUtil.getDicCodeNameMapByGroupCode(code).forEach((c, n) -> add(new HashMap<String, String> () {
                            {
                                put("code", c);
                                put("name", n);
                            }
                        }));
                    }
                });
            }
        }));
        return toResult(list);
    }
}
