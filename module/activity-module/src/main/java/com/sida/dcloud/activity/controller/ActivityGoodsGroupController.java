package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.po.ActivityGoodsGroup;
import com.sida.dcloud.activity.service.ActivityGoodsGroupService;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("activityGoodsGroup")
@Api(description = "活动商品组组信息")
public class ActivityGoodsGroupController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityGoodsGroupController.class);

    @Autowired
    private ActivityGoodsGroupService activityGoodsGroupService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查活动商品组列表")
    public Object list(@RequestBody @ApiParam("JSON参数") ActivityGoodsGroup param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = activityGoodsGroupService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动商品组id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        ActivityGoodsGroup one = activityGoodsGroupService.selectByPrimaryKey(id);
        return toResult(one);
    }

    @RequestMapping(value = "/findListByActivityId", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动id取商品")
    public Object findListByActivityId(@RequestParam("activityId") @ApiParam("活动id")String activityId) {
        return toResult(activityGoodsGroupService.findGroupListByActivityId(activityId));
    }

    @RequestMapping(value = "/findListByGoodsId", method = RequestMethod.GET)
    @ApiOperation(value = "根据商品id取商品组")
    public Object findListByGoodsId(@RequestParam("goodsId") @ApiParam("商品id")String goodsId) {
        return toResult(activityGoodsGroupService.findGroupListByGoodsId(goodsId));
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除活动商品组")
    public Object remove(@RequestBody @ApiParam("活动商品组ids") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new ActivityException("没有指定要删除的活动商品组");
        }
        activityGoodsGroupService.deleteByPrimaryKeys(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增活动商品组")
    public Object insert(@RequestBody @ApiParam("活动商品组信息JSON") ActivityGoodsGroup param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        activityGoodsGroupService.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新活动商品组")
    public Object update(@RequestBody @ApiParam("活动商品组信息JSON") ActivityGoodsGroup param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        activityGoodsGroupService.updateByPrimaryKey(param);
        return toResult();
    }

    private void checkForm(ActivityGoodsGroup param, int event) {
        String id = Optional.ofNullable(param.getId()).orElse("");
        if(EventConstants.EVENT_UPDATE == event && "".equals(id)) {
            throw new ActivityException("更新操作时主键不能空");
        }

        Optional.ofNullable(param.getName()).orElseThrow(() ->new ActivityException("必须指定组名称"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
        }
    }
}