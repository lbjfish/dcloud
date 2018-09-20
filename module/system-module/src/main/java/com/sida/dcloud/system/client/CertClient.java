package com.sida.dcloud.system.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name = "${provider.cert-module}")
public interface CertClient {

    @RequestMapping(value = "/certServiceInfo/getStudentCount",method = RequestMethod.GET)
    @ApiOperation("获取在读学员数量（状态为暂停学车或者正常的学员） 用于首页展示")
    public Object getStudentCount();
}
