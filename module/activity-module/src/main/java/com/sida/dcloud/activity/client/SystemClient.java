package com.sida.dcloud.activity.client;

import com.sida.dcloud.auth.vo.InitSystemDTO;
import com.sida.dcloud.auth.vo.UserConditionDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${provider.system-module}")
public interface SystemClient {
    @RequestMapping(value = "/sysOrg/findOrgsByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取组织")
    public Object findOrgsByIds(@RequestBody @ApiParam("JSON参数") List<String> ids);

    @RequestMapping(value = "/sysOrg/findOrgNamesByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取组织")
    public Object findOrgNamesByIds(@RequestBody @ApiParam("JSON参数") List<String> ids);

    @RequestMapping(value = "/sysOrg/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织 对象")
    public Object findOne(@RequestBody @ApiParam("JSON参数") String id);

    @RequestMapping(value = "/sysOrg/findOneVo", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织 拓展对象")
    public Object findOneVo(@RequestBody @ApiParam("JSON参数") String id);

    @RequestMapping(value = "/sysOrg/findAllVo", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织 拓展对象")
    public Object findOneVo(@RequestBody @ApiParam("JSON参数") List<String> ids);

    @RequestMapping(value = "/sysUser/findUsersByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "微服务用：根据条件获取用户列表")
    public Object findUsersByCondition(@RequestBody @ApiParam("JSON参数") UserConditionDTO param);

    @RequestMapping(value = "/sysUser/findUsersByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取用户")
    public Object findUsersByIds(@RequestBody @ApiParam("JSON参数") List<String> ids);

    @RequestMapping(value = "/sysUser/findUserNamesByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取用户姓名")
    public Object findUserNamesByIds(@RequestBody @ApiParam("JSON参数") List<String> ids);

    @PostMapping("/init/initSystem")
    @ApiOperation("初始化系统")
    public Object initSystem(@RequestBody @ApiParam("JSON参数") InitSystemDTO initSystemDTO);

    @PostMapping(value = "/sysRegion/findNameMapByCodeList")
    @ApiOperation(value = "根据地区编码code集合获取名称map")
    public Object findNameMapByCodeList(@RequestBody @ApiParam("JSON参数") List<String> param);


}
