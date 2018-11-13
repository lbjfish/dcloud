package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.ActivityInfoAndGoodsVo;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.service.IBaseService;

public interface ActivityInfoService extends IBaseService<ActivityInfo> {
    Page<ActivityInfoVo> findPageList(ActivityInfo po);
    int updateActivityStatus(String activityId, String activityStatus);
    void increaseCommentCount(String activityId, int count);
    void increaseSignCount(String activityId, int count);
    void increaseFavoriteCount(String activityId, int count);
    ActivityInfoAndGoodsVo findOneWithGoods(String activityId);
}