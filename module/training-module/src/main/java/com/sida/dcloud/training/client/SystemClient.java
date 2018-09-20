package com.sida.dcloud.training.client;

import com.sida.dcloud.auth.vo.InitSystemDTO;
import com.sida.dcloud.auth.vo.UserConditionDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${provider.system-module}")
public interface SystemClient {

    @RequestMapping(value = "/sysOrg/findAreas", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在驾校的片区数据")
    public Object findAreas();

    @RequestMapping(value = "/sysOrg/findAreaNames", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在驾校的片区数据:精简数据")
    public Object findAreaNames();

    @RequestMapping(value = "/sysOrg/findAreasByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在驾校的片区数据")
    public Object findAreasByUserId(@RequestBody @ApiParam("JSON参数") String userId);

    @RequestMapping(value = "/sysOrg/findAreaNamesByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在驾校的片区数据:精简数据")
    public Object findAreaNamesByUserId(@RequestBody @ApiParam("JSON参数") String userId);

    @RequestMapping(value = "/sysOrg/findStores", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在片区的门店数据")
    public Object findStores();

    @RequestMapping(value = "/sysOrg/findStoreNames", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在片区的门店数据:精简数据")
    public Object findStoreNames();

    @RequestMapping(value = "/sysOrg/findStoresByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在片区的门店数据")
    public Object findStoresByUserId(@RequestBody @ApiParam("JSON参数") String userId);

    @RequestMapping(value = "/sysOrg/findStoreNamesByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在片区的门店数据:精简数据")
    public Object findStoreNamesByUserId(@RequestBody @ApiParam("JSON参数") String userId);

    @RequestMapping(value = "/sysOrg/findAreasAndStores", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在驾校的片区门店数据")
    public Object findAreasAndStores();

    @RequestMapping(value = "/sysOrg/findAreaAndStoreNames", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录人所在驾校的片区门店数据:精简数据")
    public Object findAreaAndStoreNames();

    @RequestMapping(value = "/sysOrg/findAreasAndStoresByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在片区的门店数据")
    public Object findAreasAndStoresByUserId(@RequestBody @ApiParam("JSON参数") String userId);

    @RequestMapping(value = "/sysOrg/findAreaAndStoreNamesByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "获取指定用户所在片区的门店数据:精简数据")
    public Object findAreaAndStoreNamesByUserId(@RequestBody @ApiParam("JSON参数") String userId);

    @RequestMapping(value = "/sysOrg/findOrgsByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取组织")
    public Object findOrgsByIds(@RequestBody @ApiParam("JSON参数") List<String> ids);

    @RequestMapping(value = "/sysOrg/findOrgNamesByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取组织")
    public Object findOrgNamesByIds(@RequestBody @ApiParam("JSON参数") List<String> ids);

    @RequestMapping(value = "/sysOrg/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织|片区|门店 对象")
    public Object findOne(@RequestBody @ApiParam("JSON参数") String id);

    @RequestMapping(value = "/sysOrg/findOneVo", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织|片区|门店 拓展对象")
    public Object findOneVo(@RequestBody @ApiParam("JSON参数") String id);

    @RequestMapping(value = "/sysOrg/findAllVo", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织|片区|门店 拓展对象")
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


    /**
     * 更新员工类型
     * @param empId
     * @param empType
     */
    @GetMapping(value = "sysEmployee/updateEmpType")
    @ApiOperation(value = "更新员工类型")
    public Object updateEmpType(@RequestParam(value = "empId") String empId, @RequestParam(value = "empType") String empType);
}
