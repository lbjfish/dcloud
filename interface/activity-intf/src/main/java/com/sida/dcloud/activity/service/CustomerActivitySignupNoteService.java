package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.dto.ActivitySignupNoteDto;
import com.sida.dcloud.activity.dto.ActivitySignupNoteSettingDto;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.vo.CustomerActivitySignupNoteVo;
import com.sida.xiruo.po.common.TableMeta;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface CustomerActivitySignupNoteService extends IBaseService<CustomerActivitySignupNote> {
    List<TableMeta> findTableMeta();
    Page<CustomerActivitySignupNoteVo> findPageList(CustomerActivitySignupNoteVo vo);
    List<ActivitySignupNoteSettingDto> findOneToClient(String id);
    Map<String, Object> findSimpleOneToClient(String id);
    String getCurrentNoteNo();
    String getCurrentThirdPartCode();
    int resendThirdPartCode();
    Map<String, Object> insertSignupNoteAndOrder(ActivitySignupNoteDto dto);
    Map<String, Object> insertSignupNoteAndOrderWithTransaction(ActivitySignupNoteDto dto);

    void createOrderExpiredJob(ActivityOrder order);
    void dropOrderExpiredJob(String jobName);
}