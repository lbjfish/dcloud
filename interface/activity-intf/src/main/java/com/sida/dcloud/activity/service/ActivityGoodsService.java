package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.ActivityGoods;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.ActivityGoodsVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityGoodsService extends IBaseService<ActivityGoods> {
    Page<ActivityGoodsVo> findPageList(ActivityGoods po);
    List<ActivityGoodsVo> findGoodsListByActivityId(String activityId);
    List<ActivityGoodsVo> findGoodsListByGroupId(String groupId);
}