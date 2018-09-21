package com.sida.dcloud.activity.service;

import com.sida.dcloud.activity.po.ActivityRelHonored;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ActivityRelHonoredService extends IBaseService<ActivityRelHonored> {
    int deleteByScheduleId(String scheduleId);
    int saveOrUpdate(List<ActivityRelHonored> list);
    int insert(ActivityRelHonored po);
}