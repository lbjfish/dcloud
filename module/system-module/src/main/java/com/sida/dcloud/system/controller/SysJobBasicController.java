package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.po.SysJobBasic;
import com.sida.dcloud.system.service.SysJobBasicService;
import com.sida.dcloud.system.vo.SysJobBasicUpdateVo;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.BlankUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sysJobBasic")
public class SysJobBasicController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysJobBasicController.class);

    @Autowired
    private SysJobBasicService sysJobBasicService;

    @RequestMapping(value = "/getSysJobByType", method = RequestMethod.GET)
    @ApiOperation(value = "根据类型获取任务")
    public Object getSysJobByType(@RequestParam(value = "type") String type) {
        SysJobBasic condition = new SysJobBasic();
        condition.setType(type);
        List<SysJobBasic> listSys = sysJobBasicService.selectByCondition(condition);
        if(BlankUtil.isNotEmpty(listSys)){
            return toResult(listSys.get(0));
        }
        return toResult();
    }
    @RequestMapping(value = "/updateSysJobByType", method = RequestMethod.POST)
    @ApiOperation(value = "根据类型修改任务值")
    public Object updateSysJobByType(@RequestBody  SysJobBasicUpdateVo sysJobBasicUpdateVo) {
        SysJobBasic condition = new SysJobBasic();
        condition.setType(sysJobBasicUpdateVo.getType());
        List<SysJobBasic> listSys = sysJobBasicService.selectByCondition(condition);
        if(BlankUtil.isNotEmpty(listSys)){
            SysJobBasic basic = listSys.get(0);
            basic.setVal(sysJobBasicUpdateVo.getVal());
            sysJobBasicService.updateByPrimaryKey(basic);
        }
        return toResult();
    }
}