package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.dto.ActivitySignupNoteDto;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.vo.CustomerActivitySignupNoteVo;
import com.sida.xiruo.po.common.TableMeta;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface CustomerActivitySignupNoteService extends IBaseService<CustomerActivitySignupNote> {
    List<TableMeta> findTableMeta();
    Page<CustomerActivitySignupNoteVo> findPageList(CustomerActivitySignupNote po);
    ActivitySignupNoteDto findOneToClient(String id);
    String getCurrentNoteNo();
    String getCurrentThirdPartCode();
}