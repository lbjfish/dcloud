package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.service.ActivityGoodsService;
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
@RequestMapping("activityGoods")
@Api(description = "活动商品信息")
public class ActivityGoodsController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityGoodsController.class);

    @Autowired
    private ActivityGoodsService activityGoodsService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查活动商品列表")
    public Object list(@RequestBody @ApiParam("JSON参数") ActivityGoods param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = activityGoodsService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动商品id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        ActivityGoods one = activityGoodsService.selectByPrimaryKey(id);
        return toResult(one);
    }

    @RequestMapping(value = "/findListByActivityId", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动id取商品")
    public Object findListByActivityId(@RequestParam("activityId") @ApiParam("活动id")String activityId) {
        return toResult(activityGoodsService.findGoodsListByActivityId(activityId));
    }

    @RequestMapping(value = "/findListByGroupId", method = RequestMethod.GET)
    @ApiOperation(value = "根据活动id取商品")
    public Object findListByGroupId(@RequestParam("groupId") @ApiParam("组id")String groupId) {
        return toResult(activityGoodsService.findGoodsListByGroupId(groupId));
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除活动商品")
    public Object remove(@RequestBody @ApiParam("活动商品ids") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new ActivityException("没有指定要删除的活动商品");
        }
        activityGoodsService.deleteByPrimaryKeys(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增活动商品")
    public Object insert(@RequestBody @ApiParam("活动商品信息JSON") ActivityGoods param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        activityGoodsService.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新活动商品")
    public Object update(@RequestBody @ApiParam("活动商品信息JSON") ActivityGoods param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        activityGoodsService.updateByPrimaryKey(param);
        return toResult();
    }

    private void checkForm(ActivityGoods param, int event) {
        checkIdEmpty(param, event);

        Optional.ofNullable(param.getActivityId()).orElseThrow(() ->new ActivityException("必须指定活动添加商品"));
        Optional.ofNullable(param.getName()).orElseThrow(() ->new ActivityException("商品名字不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
            param.setAddTime(param.getCreatedAt());
        }
    }
}