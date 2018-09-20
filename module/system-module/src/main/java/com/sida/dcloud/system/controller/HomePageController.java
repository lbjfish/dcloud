package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.po.SysMessage;
import com.sida.dcloud.system.service.*;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzhenwei
 * 2018/03/06.
 */
@RestController
@RequestMapping("homepage")
@Api(description = "首页")
public class HomePageController extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private SysCommonService sysCommonService;

    @RequestMapping(value = "getNumbers", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取首页统计数据-学员、教练车、教练")
    public Object getNumbers() {
        Map<String,Long> map = new HashMap<>();
        map.put("stuNum",0l);
        map.put("carNum",0l);
        map.put("coachNum",0l);
        map.put("trainNum",0l);
        return toResult(map);
    }

    @RequestMapping(value = "userMessages", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取首页用户消息通知")
    public Object userMessages(@RequestBody @ApiParam("JSON参数") SysMessage param) {
        if (BlankUtil.isEmpty(param.getUserId())){
            param.setUserId(LoginManager.getCurrentUserId());
        }
        Object object = sysMessageService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "readMessage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("用户读取消息")
    public Object readMessage(@RequestBody @ApiParam("JSON参数") SysMessage param) {
        Object object = sysMessageService.readMessage(param.getId());
        return toResult(object);
    }
}
