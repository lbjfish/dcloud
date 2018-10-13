package com.sida.dcloud.operation.client;

import com.sida.dcloud.auth.vo.InitSystemDTO;
import com.sida.dcloud.auth.vo.UserConditionDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(name = "${provider.system-module}")
public interface SystemClient {
    @RequestMapping(value = "/sysUser/saveOrUpdateDto", method = RequestMethod.POST)
    @ApiOperation(value = "同步用户数据到系统用户表")
    Object saveOrUpdateDto(@RequestBody @ApiParam("JSON参数") Map<String, Object> map);
}
