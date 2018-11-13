package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysRegion;
import com.sida.dcloud.system.common.SystemCacheUtil;
import com.sida.dcloud.system.service.SysRegionService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sysRegion")
@Api(description = "地区管理api")
public class SysRegionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysRegionController.class);

    @Autowired
    private SysRegionService sysRegionService;

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ApiOperation(value = "初始化地区数据")
    public Object init() {
        sysRegionService.init();
        return toResult();
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ApiOperation(value = "获取地区树")
    public Object tree() {
        Object object = sysRegionService.findTree();
        return toResult(object);
    }

    @RequestMapping(value = "/threelevel", method = RequestMethod.GET)
    @ApiOperation(value = "获取地区树（国家省份城市）")
    public Object threelevel() {
        Object object = sysRegionService.findThreeLevelTree();
        return toResult(object);
    }

    @RequestMapping(value = "/singlelevel", method = RequestMethod.GET)
    @ApiOperation(value = "根据级别获取扁平化地区数据")
    public Object singlelevel(@RequestParam("level") @ApiParam("层级（COUNTRY, PROVINCE, CITY, AREA）") String level) {
        Object object = sysRegionService.findSysRegionSingleLayerDtoByLevel(level);
        return toResult(object);
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新区域")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") SysRegion param) {

        if (StringUtils.isBlank(param.getId())){
            PoDefaultValueGenerator.putDefaultValue(param);
            if (StringUtils.isNotBlank(param.getParentId())){
                SysRegion parent = sysRegionService.selectByPrimaryKey(param.getParentId());
                if (parent!=null){
                    param.setPath(parent.getPath());
                    param.setNamePath(parent.getNamePath());
                    if (BlankUtil.isNotEmpty(parent.getCode()) && parent.getCode().equals(SecConstant.CHINA)){
                        param.setCodePath("");
                    }else {
                        param.setCodePath(parent.getCode());
                    }
                }
            }
            param.setPath((param.getPath()==null?"":param.getPath()) + param.getId() + ",");
            param.setNamePath((param.getNamePath()==null?"":param.getNamePath()) + param.getName() + ",");
            param.setCodePath((param.getCodePath()==null?"":param.getCodePath()) + param.getCode() + ",");
            sysRegionService.insertSelective(param);
        }else {
            PoDefaultValueGenerator.putDefaultValue(param);
            sysRegionService.updateByPrimaryKeySelective(param);
        }
        return toResult();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "删除区域信息")
    public Object delete(@PathVariable("id") String id) {
        sysRegionService.deleteById(id);
        return toResult();
    }

    @RequestMapping(value = "/createRegionXml", method = RequestMethod.POST)
    @ApiOperation(value = "生成地区信息xml文件")
    public Object createRegionXml() {
        sysRegionService.createRegionXml();
        return toResult();
    }

    @RequestMapping(value = "/findCitys", method = RequestMethod.GET)
    @ApiOperation(value = "获取城市集合（含热门城市）")
    public Object findCitys() {
        Object object = sysRegionService.findCitys();
        return toResult(object);
    }

    @PostMapping(value = "/findNameMapByCodeList")
    @ApiOperation(value = "根据地区编码code集合获取名称map")
    public Object findNameMapByCodeList(@RequestBody @ApiParam("JSON参数") List<String> param) {
        Object object = sysRegionService.findNameMapByCodeList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/getNameByCode", method = RequestMethod.GET)
    @ApiOperation(value = "根据编码查询名称")
    public String getNameByCode(String code) {
        return sysRegionService.getNameByCode(code);
    }

    @Autowired
    private SystemCacheUtil systemCacheUtil;

    @RequestMapping(value = "/clearRegionDatasInRedis", method = RequestMethod.GET)
    @ApiOperation(value = "从redis清空地区数据")
    public Object clearRegionDatasInRedis() {
        systemCacheUtil.clearRegionDatasInRedis();
        return toResult();
    }
}