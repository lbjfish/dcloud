package com.sida.dcloud.system.controller.tm;

import com.sida.dcloud.auth.vo.InitSystemDTO;
import com.sida.dcloud.compensate.dto.CompensateDto;
import com.sida.dcloud.system.controller.customer.SysUserCustomerController;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * TODO: 2018/10/22
 * 补偿请求/通知放在system中并不健壮，如果触发事务补偿的服务是system则无法决策和通知到，待以后完善以稳定的服务功能集群部署
 * 同时补偿决策方案需要斟酌
 */
@RestController
@RequestMapping("compensate")
@Api(description = "分布式事务补偿")
public class CompensateActionController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensateActionController.class);

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ApiOperation("补偿通知")
    public Object notify(@RequestBody @ApiParam("事务补偿数据结构") CompensateDto compensateDto) {
        Optional.ofNullable(compensateDto.getAction()).orElseThrow(() -> new ServiceException("没有指定补偿动作"));
        if("notify".equals(compensateDto.getAction())) {
            LOG.warn("收到补偿结果通知，groupId={}, action={}, resState={}", compensateDto.getGroupId(), compensateDto.getAction(), compensateDto.getResState());
        } else if("compensate".equals(compensateDto.getAction())) {
            //TODO 决策方案待补充
            LOG.error("事务出现异常，收到决策请求，直接返回SUCCESS进行自动补偿，groupId={}, action={}, json={}", compensateDto.getGroupId(), compensateDto.getAction(), compensateDto.getJson());
        } else {
            throw new ServiceException("无效的补偿动作");
        }
        return "SUCCESS";
    }
}
