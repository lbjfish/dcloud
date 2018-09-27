package com.sida.dcloud.activity.service;

import com.sida.dcloud.activity.po.ActivityRelHonored;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ActivityRelHonoredService extends IBaseService<ActivityRelHonored> {
    String LOCK_KEY_CHECK_REMOVABLE = "LOCK_KEY_CHECK_REMOVABLE_" + ActivityRelHonoredService.class.getName();

    int deleteByScheduleId(String scheduleId);
    int saveOrUpdate(List<ActivityRelHonored> list);
}