package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.po.SysCacheVersion;
import com.sida.dcloud.system.service.SysCacheVersionService;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 记录业务数据的最后更新状态（增删改），状态变化决定应用程序初始化时是否更新Redis缓存
 1、应用程序第一次初始化时，将CacheVersion记录缓存到redis，
    以后每次初始化，先将redis缓存与数据库缓存版本表进行比对，
 如果有差异，则更新差异对应的缓存数据，并同时更新缓存版本与数据库保持一致。
    如果没有差异，则redis不更新缓存
 2、当缓存的业务数据，有更新时，除了实时更新缓存和缓存版本之外，还需要同时更新数据库缓存版本表。
 created by huangbaidong
 2017-10-27
 */
@RestController
@RequestMapping("sysCacheVersion")
@Api(description = "缓存版本管理")
public class SysCacheVersionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysCacheVersionController.class);

    @Autowired
    private SysCacheVersionService sysCacheVersionService;

    @ResponseBody
    @GetMapping("findAll")
    @ApiOperation("查询所有缓存版本")
    public Object findAll() {
        List<SysCacheVersion> versions = sysCacheVersionService.findAll();
        return toResult(versions);
    }

    @GetMapping("saveOrUpdate")
    @ApiOperation("更新缓存版本")
    public void updateCacheVersion(@RequestParam String redisKey) {
        sysCacheVersionService.updateCacheVersion(redisKey);
    }
}