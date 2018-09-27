package com.sida.dcloud.activity.service;

import com.sida.dcloud.activity.po.ActivityGoodsRelGroup;
import com.sida.dcloud.activity.po.ActivityRelHonored;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ActivityGoodsRelGroupService extends IBaseService<ActivityGoodsRelGroup> {
    String LOCK_KEY_CHECK_REMOVABLE = "LOCK_KEY_CHECK_REMOVABLE_" + ActivityGoodsRelGroupService.class.getName();

    int deleteByGroupId(String groupId);
    int saveOrUpdate(List<ActivityGoodsRelGroup> list);
}