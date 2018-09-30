package com.sida.dcloud.activity.service;

import com.sida.dcloud.activity.po.ActivityOrderGoods;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ActivityOrderGoodsService extends IBaseService<ActivityOrderGoods> {
    List<ActivityOrderGoods> findListByOrderId(String orderId);
    int batchInsert(List<ActivityOrderGoods> list);
}