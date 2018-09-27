package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.po.ActivityGoodsRelGroup;
import com.sida.dcloud.activity.po.ActivityRelHonored;
import com.sida.dcloud.activity.service.ActivityGoodsRelGroupService;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("activityGoodsRelGroup")
@Api(description = "活动商品与商品组多对多关联")
public class ActivityGoodsRelGroupController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityGoodsRelGroupController.class);

    @Autowired
    private ActivityGoodsRelGroupService activityGoodsRelGroupService;

    @RequestMapping(value = "/removeByGroupId", method = RequestMethod.GET)
    @ApiOperation(value = "根据组删除商品关联关系")
    public Object removeByGroupId(@RequestParam("groupId") @ApiParam("活动安排id") String groupId) {
        return toResult(activityGoodsRelGroupService.deleteByGroupId(groupId));
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "根据组更新商品关联关系")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON集合") List<ActivityGoodsRelGroup> poList) {
        return toResult(activityGoodsRelGroupService.saveOrUpdate(poList));
    }
}