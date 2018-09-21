package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.HonoredGuest;
import com.sida.dcloud.activity.vo.HonoredGuestVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface HonoredGuestService extends IBaseService<HonoredGuest> {
    Page<HonoredGuestVo> findPageList(HonoredGuest po);
    int checkMultiCountByUnique(HonoredGuest po);
    List<HonoredGuestVo> findVoListByScheduleId(String scheduleId);
}