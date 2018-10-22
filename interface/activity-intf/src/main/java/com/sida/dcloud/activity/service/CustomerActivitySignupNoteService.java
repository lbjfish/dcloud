package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.dto.ActivitySignupNoteDto;
import com.sida.dcloud.activity.dto.ActivitySignupNoteSettingDto;
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
    Map<String, String> findSimpleOneToClient(String id);
    String getCurrentNoteNo();
    String getCurrentThirdPartCode();

    Map<String, String> insertSignupNoteAndOrder(ActivitySignupNoteDto dto);
}