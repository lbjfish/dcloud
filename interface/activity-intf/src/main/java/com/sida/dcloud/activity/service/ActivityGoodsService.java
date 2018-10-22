package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.vo.ActivityGoodsVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ActivityGoodsService extends IBaseService<ActivityGoods> {
    Page<ActivityGoodsVo> findPageList(ActivityGoods po);
    List<ActivityGoodsVo> findGoodsListByActivityId(String activityId);
    List<ActivityGoodsVo> findGoodsListByActivityIds(String activityIds);
    List<ActivityGoodsVo> findGoodsListByGroupId(String groupId);
    List<ActivityGoods> findListByIds(String ids);
    Double getTotalPayPriceByIds(String ids);
    int upByPrimaryKeys(String ids);
    int downByPrimaryKeys(String ids);
}