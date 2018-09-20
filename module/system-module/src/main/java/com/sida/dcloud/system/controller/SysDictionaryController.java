package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.system.po.SysDictionary;
import com.sida.dcloud.system.service.SysDictionaryService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.BuildTree;
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
@RequestMapping("sysDictionary")
@Api(description = "字典API")
public class SysDictionaryController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryController.class);

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ApiOperation(value = "获取字典树")
    public Object tree() {
        SysDictionary condition = new SysDictionary();
        condition.setDisable(false);
        condition.setDeleteFlag(false);
        condition.setOrderField(SecConstant.SORT);
        condition.setOrderString(SecConstant.ASC);
        List<SysDictionary> dicList = sysDictionaryService.selectByCondition(condition);
        dicList = BuildTree.buildTree(dicList);
        return toResult(dicList);
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新字典")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") SysDictionary param) {
        if (StringUtils.isBlank(param.getId())){
            PoDefaultValueGenerator.putDefaultValue(param);
            if (StringUtils.isNotBlank(param.getParentId())){
                SysDictionary parent = sysDictionaryService.selectByPrimaryKey(param.getParentId());
                if (parent!=null){
                    param.setPath(parent.getPath());
                }
            }
            param.setPath((param.getPath()==null?"":param.getPath()) + param.getId() + ".");
            sysDictionaryService.insertSelective(param);
        }else {
            PoDefaultValueGenerator.putDefaultValue(param);
            sysDictionaryService.updateByPrimaryKeySelective(param);
        }
        return toResult();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "删除字典信息")
    public Object delete(@PathVariable("id") String id) {
        sysDictionaryService.deleteById(id);
        return toResult();
    }

    @RequestMapping(value = "/selectByPCode", method = RequestMethod.GET)
    @ApiOperation(value = "通过父级PCode获取子级字典")
    public Object selectByPCode(@RequestParam("pCode") String pCode) {
        Object object = sysDictionaryService.selectByPCode(pCode);
        return toResult(object);
    }

    @RequestMapping(value = "/selectByPid", method = RequestMethod.POST)
    @ApiOperation(value = "通过父级id获取子级字典")
    public Object selectByPid(@RequestParam("pid") String pid) {
        Object object = sysDictionaryService.selectByPid(pid);
        return toResult(object);
    }


    @RequestMapping(value = "/loadDictsToRedis", method = RequestMethod.GET)
    @ApiOperation(value = "加载数据字典到Redis")
    public Object loadDictsToRedis() {
        sysDictionaryService.loadDictsToRedis();
        return toResult();
    }

}