package com.sida.dcloud.system.client;

import com.sida.dcloud.auth.vo.InitStoreDTO;
import com.sida.dcloud.auth.vo.SysOrgVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//@FeignClient(name = "${provider.marketing-module}")
public interface MarketingClient {

    @RequestMapping(value = "/marketStore/incrSyncInitStores", method = RequestMethod.POST)
    @ApiOperation(value = "增量同步触发刷新门店")
    Object incrSyncInitStores(@RequestBody @ApiParam("JSON参数") InitStoreDTO param);

    @RequestMapping(value = "/marketStore/initAllStore", method = RequestMethod.POST)
    @ApiOperation(value = "初始化全量录入门店")
    Object initAllStore(@RequestBody @ApiParam("JSON参数") List<SysOrgVo> param);

}
