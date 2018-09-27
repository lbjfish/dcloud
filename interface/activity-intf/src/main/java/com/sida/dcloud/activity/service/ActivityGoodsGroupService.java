package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.po.ActivityGoodsGroup;
import com.sida.dcloud.activity.vo.ActivityGoodsGroupVo;
import com.sida.dcloud.activity.vo.ActivityGoodsVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ActivityGoodsGroupService extends IBaseService<ActivityGoodsGroup> {
    Page<ActivityGoodsGroupVo> findPageList(ActivityGoodsGroup po);
    List<ActivityGoodsGroupVo> findGroupListByActivityId(String activityId);
    List<ActivityGoodsGroupVo> findGroupListByGoodsId(String goodsId);
}