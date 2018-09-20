package com.sida.security.edge.client;

import com.sida.dcloud.system.po.SysAccessLogDetail;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${provider.system-module}")
public interface SystemClient {

    @RequestMapping(value = "/sysAccessLog/insertAccessLogDetail", method = RequestMethod.POST)
    @ApiOperation(value = "通过父级PCode获取子级字典")
    void insertAccessLogDetail(@RequestBody SysAccessLogDetail detail);
}
