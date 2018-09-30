package com.sida.dcloud.activity.service;

import com.sida.dcloud.activity.po.ActivityOrderGoods;
import com.sida.dcloud.activity.po.ActivityOrderGoodsGroup;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ActivityOrderGoodsGroupService extends IBaseService<ActivityOrderGoodsGroup> {
    List<ActivityOrderGoodsGroup> findListByOrderId(String orderId);
    List<ActivityOrderGoodsGroup> findListByIds(String ids);
    int batchInsert(List<ActivityOrderGoodsGroup> list);
}