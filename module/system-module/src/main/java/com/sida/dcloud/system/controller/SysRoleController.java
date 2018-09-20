package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysRole;
import com.sida.dcloud.auth.vo.SysRoleVo;
import com.sida.dcloud.system.service.SysRoleService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.BuildKeyValueMapUtil;
import com.sida.xiruo.xframework.util.FormCheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("sysRole")
@Api(description = "角色管理api")
public class SysRoleController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色列表")
    public Object list(@RequestBody @ApiParam("JSON参数") SysRole param) {
        Object object = sysRoleService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新角色")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") SysRoleVo param) {
        //新增校验code不为空且唯一
        if (StringUtils.isBlank(param.getId())){
            sysRoleService.checkCode(param.getCode());
        }
        sysRoleService.saveRole(param);
        return toResult();
    }

    @RequestMapping(value = "/getInfo/{roleId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取角色详情")
    public Object getInfo(@PathVariable("roleId") String roleId) {
        Object object = sysRoleService.findOneVo(roleId);
        return toResult(object);
    }

    @RequestMapping(value = "/enableRoles", method = RequestMethod.POST)
    @ApiOperation(value = "批量启用角色")
    public Object enableRoles(@RequestBody @ApiParam("JSON参数")List<String> ids) {
        SysRole po = new SysRole();
        po.setStatus(false);
        sysRoleService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/disableRoles", method = RequestMethod.POST)
    @ApiOperation(value = "批量禁用角色")
    public Object disableRoles(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        SysRole po = new SysRole();
        po.setStatus(true);
        sysRoleService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/selectList", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色候选列表")
    public Object selectList() {
        SysRole condition = new SysRole();
        condition.setDisable(false);
        condition.setDeleteFlag(false);
        condition.setStatus(false);
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysRole> roleList = sysRoleService.selectByCondition(condition);
        //封装list为map，减少多余的字段
        return toResult(BuildKeyValueMapUtil.change(roleList));
    }

    @RequestMapping(value = "/checkIfEmpLikeRoleScope", method = RequestMethod.GET)
    @ApiOperation(value = "检查员工是否属于某一角色范围")
    public boolean checkIfEmpLikeRoleScope(@RequestParam("userId") String empId,
                               @RequestParam("roleScope") String roleScope) {
        boolean result = sysRoleService.checkIfEmpLikeRoleScope(empId, roleScope);
        return result;
    }

    @RequestMapping(value = "/checkIfEmpInRoleScope", method = RequestMethod.GET)
    @ApiOperation(value = "检查员工是否属于某一角色范围")
    public boolean checkIfEmpInRoleScope(@RequestParam("userId") String empId,
                                         @RequestParam("roleScope") List<String> roles) {
        boolean result = sysRoleService.checkIfEmpInRoleScope(empId, roles);
        return result;
    }

    @RequestMapping(value = "/getRecommendEmpTypeByEmpId", method = RequestMethod.GET)
    @ApiOperation(value = "得到推荐员工类型")
    public String getRecommendEmpTypeByEmpId(@RequestParam("userId") String empId) {
        FormCheckUtil.checkRequiredField(empId, "员工ID");
        String recommendEmpType = "2";//默认其他员工
        boolean isCoach = sysRoleService.checkIfEmpLikeRoleScope(empId,"教练");
        if(isCoach) {
            recommendEmpType = "1";//教练
        } else {
            boolean isService = sysRoleService.checkIfEmpInRoleScope(empId, Arrays.asList("客服专员","顶班客服","储备客服"));
            if(isService) {
                recommendEmpType = "3";//客服
            }
        }
        return recommendEmpType;
    }
}