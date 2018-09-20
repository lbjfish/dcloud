package com.sida.dcloud.system.client;

import com.sida.dcloud.auth.po.SysOrg;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//@FeignClient(name = "${provider.car-module}")
public interface CarClient {

    @RequestMapping(value = "/init/initCarAuthOrgAndRegAuthor", method = RequestMethod.POST)
    @ApiOperation(value = "初始化车务所属权与登记机关")
    Object initCarAuthOrgAndRegAuthor(@RequestBody @ApiParam("JSON参数") List<SysOrg> param);

    @RequestMapping(value = "carInfo/getTeachingCarNum", method = RequestMethod.GET)
    @ApiOperation(value = "统计教练车数量")
    Object getTeachingCarNum();
}
