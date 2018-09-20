package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.po.SysAccessLogDetail;
import com.sida.dcloud.system.service.SysAccessLogDetailService;
import com.sida.dcloud.system.vo.SysAccessLogDTO;
import com.sida.xiruo.xframework.controller.BaseController;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("sysAccessLog")
public class SysAccessLogController extends BaseController {

    @Resource
    private SysAccessLogDetailService sysAccessLogDetailService;

    @PostMapping("/list")
    @ApiOperation(value = "日志列表", response = SysAccessLogDetail.class)
    public Object list(@RequestBody SysAccessLogDetail vo) {
        Page<SysAccessLogDetail> pageList = sysAccessLogDetailService.pageList(vo);
        return toResult(pageList);
    }

    @RequestMapping(value = "/insertAccessLogDetail", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新字典")
    public void insertAccessLogDetail(@RequestBody SysAccessLogDetail detail) {
        sysAccessLogDetailService.insert(detail);
    }

    @RequestMapping(value="/listByDate", method = RequestMethod.POST)
    @ApiOperation(value = "根据时间段查询日志列表", response = SysAccessLogDetail.class)
    public Object selectByDate(@RequestBody SysAccessLogDTO dto) {
        Page<SysAccessLogDetail> pageList = sysAccessLogDetailService.selecAccessLogstByDate(dto.getP(),dto.getS(),dto.getStartTime(),dto.getEndTime());
        return toResult(pageList);
    }

}
