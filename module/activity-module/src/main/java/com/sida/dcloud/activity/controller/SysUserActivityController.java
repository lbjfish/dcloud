package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.service.SysUserActivityService;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sysUserActivity")
@Api(description = "系统用户在活动模块的投影")
public class SysUserActivityController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysUserActivityController.class);

    @Autowired
    private SysUserActivityService sysUserActivityService;

    @RequestMapping(value="batchUpdate", method = RequestMethod.POST)
    @ApiOperation("批量更新用户数据")
    public Object batchUpdate(@RequestBody @ApiParam("用户集合") List<SysUser> userList) {
        if(userList.isEmpty()) throw new ActivitiException("用户列表不能空");
        return toResult(sysUserActivityService.updateUserList(userList));
    }

    @RequestMapping(value="update", method = RequestMethod.POST)
    @ApiOperation("更新用户数据")
    public Object update(@RequestBody @ApiParam("用户") SysUser user) {
        if(user == null) throw new ActivitiException("用户不能空");
        return toResult(sysUserActivityService.updateUser(user));
    }
}