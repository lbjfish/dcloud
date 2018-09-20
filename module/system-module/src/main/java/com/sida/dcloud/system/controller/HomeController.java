package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.vo.UserInfo;
import com.sida.dcloud.system.service.SysCacheVersionService;
import com.sida.dcloud.system.service.SysDictionaryDataService;
import com.sida.dcloud.system.service.SysOrgService;
import com.sida.dcloud.system.service.SysUserService;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbaidong
 * 2017/10/27.
 */
@RestController
@RequestMapping("home")
@Api(description = "首页")
public class HomeController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private SysDictionaryDataService sysDictionaryDataService;

    @Autowired
    private SysCacheVersionService sysCacheVersionService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("load")
    @ApiOperation("加载初始数据")
    public Object load() {
        /**
         * 1、获取登录用户信息
         */
        UserInfo userInfo = sysUserService.getUserInfo();

        /**
         * 2、获取缓存版本
         */
        Map<String, String> cacheVersion = null;
        try {
            cacheVersion = redisUtil.getCacheVersion();
        } catch (Exception e) {
            cacheVersion = sysCacheVersionService.findAllForCache();
        }
        Map<String, Object> datas = new HashMap<>();
        datas.put("userInfo", userInfo);
        datas.put("cacheVersion", cacheVersion);
        return toResult(datas);
    }

    /**
     * @return
     */
    @GetMapping("loadDictCache")
    @ApiOperation("加载数据字典缓存")
    public Object loadDictCache() {
        //org_map版本有差异时，前端调用此接口更新数据字典缓存
        Map dicts = null;
        try {
            dicts = redisUtil.getEntriesFromMap(RedisKey.DICT_DATA);
        } catch(Exception e) {
            //redis异常时，查数据库
            dicts = sysDictionaryDataService.findAllForCache();
        }
        return toResult(dicts);
    }

    @GetMapping("loadDictCacheForApp")
    @ApiOperation("加载数据字典缓存")
    public Object loadDictCacheForApp() {
        //org_map版本有差异时，前端调用此接口更新数据字典缓存
        Map<String, List> dicts = new HashMap<>();
        Map<String, Map<String, String>> groupDataMap = null;
        try {
            groupDataMap = redisUtil.getEntriesFromMap(RedisKey.DICT_DATA);
        } catch(Exception e) {
            //redis异常时，查数据库
            groupDataMap = sysDictionaryDataService.findAllForCache();
        }
        if(BlankUtil.isNotEmpty(groupDataMap)) {
            for(String key : groupDataMap.keySet()) {
                List groupList = new ArrayList();
                Map<String, String> dictDataMap = groupDataMap.get(key);
                for(String dictKey : dictDataMap.keySet()) {
                    Map item = new HashMap();
                    item.put("value", dictKey);
                    item.put("label", dictDataMap.get(dictKey));
                    groupList.add(item);
                }
                dicts.put(key, groupList);
            }
        }
        return toResult(dicts);
    }

    /**
     * 版本有差异时，前端调用此接口更新组织机构缓存
     * @return
     */
    @GetMapping("loadOrgCache")
    @ApiOperation("加载组织机构缓存")
    public Object loadOrgCache() {

        Map<String, String> orgMap = null;
        try {
            orgMap = redisUtil.getOrgMap();
        } catch(Exception e) {
            //redis异常时直接去数据库
            orgMap = sysOrgService.findAllForCache();
        }

        List<Map<String, Object>> allAreas = sysOrgService.findAllAreas();
        List<Map<String, Object>> allStores = sysOrgService.findAllStores();

        Map<String, Object> result = new HashMap<>();

        Map<String, List<Map<String, Object>>> areaStoreMap = new HashMap<>();
        Map<String, List<Map<String, Object>>> orgAreaMap = new HashMap<>();

        /**
         * 组装驾校片区分组缓存
         */
        if(BlankUtil.isNotEmpty(allAreas)) {
            for (Map<String, Object> area : allAreas) {
                Object orgId = area.get("orgId");
                if(orgId != null) {
                    if (orgAreaMap.containsKey(orgId.toString())) {
                        List<Map<String, Object>> areas = orgAreaMap.get(orgId.toString());
                        areas.add(area);
                    } else {
                        List<Map<String, Object>> areas = new ArrayList<>();
                        areas.add(area);
                        orgAreaMap.put(orgId.toString(), areas);
                    }
                }
            }
        }

        /**
         * 组装片区门店分组缓存
         */
        if(BlankUtil.isNotEmpty(allStores)) {
            for (Map<String, Object> store : allStores) {
                Object areaId = store.get("areaId");
                if(areaId != null) {

                    if (areaStoreMap.containsKey(areaId.toString())) {
                        List<Map<String, Object>> stores = areaStoreMap.get(areaId.toString());
                        stores.add(store);
                    } else {
                        List<Map<String, Object>> stores = new ArrayList<>();
                        stores.add(store);
                        areaStoreMap.put(areaId.toString(), stores);
                    }
                }
            }
        }

        result.put("orgMap", orgMap);
        result.put("areaList", allAreas);
        result.put("areaStoreMap", areaStoreMap);
        result.put("orgAreaMap", orgAreaMap);
        return toResult(result);
    }
}
