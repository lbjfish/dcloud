package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.ActivitySchedule;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.ActivityScheduleVo;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface ActivityScheduleService extends IBaseService<ActivitySchedule> {
    Page<ActivityScheduleVo> findPageList(ActivitySchedule po);
    List<ActivityScheduleVo> findVoListByHonoredGuestId(String honoredGuestId);
}